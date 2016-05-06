package org.hly.cloudnote.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hly.cloudnote.dao.FileDao;
import org.hly.cloudnote.dao.ShareDao;
import org.hly.cloudnote.dao.UserDao;
import org.hly.cloudnote.entity.File;
import org.hly.cloudnote.entity.Share;
import org.hly.cloudnote.entity.User;
import org.hly.cloudnote.util.NoteResult;
import org.hly.cloudnote.util.NoteUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
// 扫描
@Transactional
public class UserServiceImpl implements UserService {
	@Resource
	// 注入
	private UserDao userDao;
	@Resource
	private FileDao fileDao;
	@Resource
	private ShareDao shareDao;

	@Transactional(readOnly = true, rollbackFor = IOException.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	// chelogin是一个整体，有错误就取消 只读操作 查询一般加入只读
	// 只有运行异常才回滚。也可以rollbakcFor 指定抛出的非运行时异常,propagation是事务的类型,isolation是隔离的级别
	public NoteResult checkLogin(String name, String password) throws Exception {
		NoteResult result = new NoteResult();
		// 检测用户名和密码
		User user = userDao.findByName(name);
		if (user == null) {
			result.setStatus(1);
			result.setMsg("用户不存在");
			return result;
		}
		// 将用户输入password加密
		String md5_pwd = NoteUtil.md5(password);
		// 密文对比
		if (!user.getCn_user_password().equals(md5_pwd)) {
			result.setStatus(2);
			result.setMsg("密码错误");
			return result;
		}
		result.setStatus(0);
		result.setMsg("登录成功");
		// 登陆成功后 将登陆id存入data
		result.setData(user.getCn_user_id());
		return result;
	}

	@Override
	public NoteResult registUser(String name, String password, String nick)
			throws Exception {
		NoteResult result = new NoteResult();
		User user = new User();
		// 用户名唯一性检测
		if (userDao.findByName(name) != null) {
			result.setStatus(1);
			result.setMsg("用户名已经存在");
			return result;
		}
		// 添加执行
		user.setCn_user_id(NoteUtil.createId()); // 设置用户id
		user.setCn_user_name(name);// 设置用户名
		user.setCn_user_nick(nick);// 添加昵称
		user.setCn_user_password(NoteUtil.md5(password));// 设置加密密码
		userDao.save(user);// 添加用户
		result.setStatus(0);
		result.setMsg("注册用户名成功");
		// 模拟下一步操作出异常
		// String s=null;s.length();
		return result;
	}

	@Override
	public NoteResult changePwd(String userId, String lastpwd, String newpwd)
			throws Exception {
		NoteResult result = new NoteResult();
		User user = userDao.findByUserId(userId);
		if (user == null) {
			result.setStatus(1);
			result.setMsg("未找到用户");
			return result;
		}
		// 将用户输入password加密
		String md5_pwd = NoteUtil.md5(lastpwd);
		// 密文对比
		if (!user.getCn_user_password().equals(md5_pwd)) {
			result.setStatus(2);
			result.setMsg("密码错误");
			return result;
		}
		//账号密码ok
		newpwd = NoteUtil.md5(newpwd);
		 userDao.changePwd(userId,newpwd);
		result.setStatus(0);
		result.setMsg("更新密码成功！");
		return result;
	}

	@Override
	public NoteResult loadFriends(String userId) {
		NoteResult result =new NoteResult();
		User user=userDao.findByUserId(userId);
		String friends=user.getCn_user_friend();
		List<User> friendsss=new ArrayList<User>();
		if(friends!=null){
		    String []friendss=friends.split("%"); 
			for (String friendId : friendss) {
				User friend=userDao.findByUserId(friendId);
				friendsss.add(friend);
			}
		}
       result.setStatus(0);
       result.setMsg("读取好友列表成功！");		
		result.setData(friendsss);
		return result;
	}

	@Override
	public NoteResult addFriend(String userId,String name) {
		NoteResult result = new NoteResult();
		User  user = userDao.findByName(name);
		if(user==null){
			result.setStatus(1);
			result.setMsg("未找到此用户！");
			return result;
		}
		//获取添加好友的id
		String friendsId=user.getCn_user_id();
		User my=userDao.findByUserId(userId);
		//获得我添加过的好友
		String allFriends=my.getCn_user_friend();
		if (allFriends != null) {
			String[] friends = allFriends.split("%");
			for (String frien : friends) {
				if (frien.equals(friendsId)) {
					result.setStatus(1);
					result.setMsg("您已经添加过此好友");
					return result;
				}
			}
			allFriends += friendsId;
			allFriends += "%";
            userDao.addFriend(userId,allFriends);
			result.setStatus(0);
			result.setMsg("添加好友成功！");
			result.setData(user);
			return result;
		}
		allFriends = friendsId + "%";
        userDao.addFriend(userId,allFriends);
		result.setStatus(0);
		result.setMsg("添加好友成功！");
		result.setData(user);
		return result;
	}

	@Override
	public NoteResult deleteFriend(String userId, String friendId) {
		NoteResult result = new NoteResult();
		User user = userDao.findByUserId(userId);
        String allFriend=user.getCn_user_friend();
        String [] friends=allFriend.split("%");
        String newFriend="";
      for (String friend : friends) {
    		if (friend.equals(friendId)) {
                result.setStatus(0);
    			result.setMsg("删除好友成功！");
    			continue;
    		}
    		newFriend+=friend;
    		newFriend+="%";
		}
      userDao.addFriend(userId, newFriend);
      return result;
	}

	@Override
	public NoteResult loadFriendFile(String friendId) {
		  NoteResult result = new NoteResult();
		  User user=userDao.findByUserId(friendId);
		  String name=user.getCn_user_name();
		 List<File> files=fileDao.findByUserName(name);
		List<Share> shares=shareDao.findByUserName(name);
		List<List> datas=new ArrayList<List>();
		datas.add(shares);
		datas.add(files);
		result.setStatus(0);
		result.setMsg("载入关注好友信息成功！");
		result.setData(datas);
		return result;
	}
}
