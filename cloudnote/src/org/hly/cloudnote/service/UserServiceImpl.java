package org.hly.cloudnote.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.hly.cloudnote.dao.UserDao;
import org.hly.cloudnote.entity.User;
import org.hly.cloudnote.util.NoteResult;
import org.hly.cloudnote.util.NoteUtil;

@Service("userService")
// 扫描
@Transactional
public class UserServiceImpl implements UserService {
	@Resource
	// 注入
	private UserDao userDao;
	@Transactional(readOnly=true,rollbackFor=IOException.class,propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)//chelogin是一个整体，有错误就取消  只读操作   查询一般加入只读   
	 //只有运行异常才回滚。也可以rollbakcFor 指定抛出的非运行时异常,propagation是事务的类型,isolation是隔离的级别
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
        //登陆成功后 将登陆id存入data
		result.setData(user.getCn_user_id());
		return result;
	}

	@Override
	public NoteResult registUser(String name, String password, String nick)
			throws Exception {
		NoteResult result = new NoteResult();
		User user = new User();
		// 用户名唯一性检测
         if(userDao.findByName(name)!=null){
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
		//模拟下一步操作出异常
//   		String s=null;s.length();
		return result;
	}

}
