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
// ɨ��
@Transactional
public class UserServiceImpl implements UserService {
	@Resource
	// ע��
	private UserDao userDao;
	@Resource
	private FileDao fileDao;
	@Resource
	private ShareDao shareDao;

	@Transactional(readOnly = true, rollbackFor = IOException.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	// chelogin��һ�����壬�д����ȡ�� ֻ������ ��ѯһ�����ֻ��
	// ֻ�������쳣�Żع���Ҳ����rollbakcFor ָ���׳��ķ�����ʱ�쳣,propagation�����������,isolation�Ǹ���ļ���
	public NoteResult checkLogin(String name, String password) throws Exception {
		NoteResult result = new NoteResult();
		// ����û���������
		User user = userDao.findByName(name);
		if (user == null) {
			result.setStatus(1);
			result.setMsg("�û�������");
			return result;
		}
		// ���û�����password����
		String md5_pwd = NoteUtil.md5(password);
		// ���ĶԱ�
		if (!user.getCn_user_password().equals(md5_pwd)) {
			result.setStatus(2);
			result.setMsg("�������");
			return result;
		}
		result.setStatus(0);
		result.setMsg("��¼�ɹ�");
		// ��½�ɹ��� ����½id����data
		result.setData(user.getCn_user_id());
		return result;
	}

	@Override
	public NoteResult registUser(String name, String password, String nick)
			throws Exception {
		NoteResult result = new NoteResult();
		User user = new User();
		// �û���Ψһ�Լ��
		if (userDao.findByName(name) != null) {
			result.setStatus(1);
			result.setMsg("�û����Ѿ�����");
			return result;
		}
		// ���ִ��
		user.setCn_user_id(NoteUtil.createId()); // �����û�id
		user.setCn_user_name(name);// �����û���
		user.setCn_user_nick(nick);// ����ǳ�
		user.setCn_user_password(NoteUtil.md5(password));// ���ü�������
		userDao.save(user);// ����û�
		result.setStatus(0);
		result.setMsg("ע���û����ɹ�");
		// ģ����һ���������쳣
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
			result.setMsg("δ�ҵ��û�");
			return result;
		}
		// ���û�����password����
		String md5_pwd = NoteUtil.md5(lastpwd);
		// ���ĶԱ�
		if (!user.getCn_user_password().equals(md5_pwd)) {
			result.setStatus(2);
			result.setMsg("�������");
			return result;
		}
		//�˺�����ok
		newpwd = NoteUtil.md5(newpwd);
		 userDao.changePwd(userId,newpwd);
		result.setStatus(0);
		result.setMsg("��������ɹ���");
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
       result.setMsg("��ȡ�����б�ɹ���");		
		result.setData(friendsss);
		return result;
	}

	@Override
	public NoteResult addFriend(String userId,String name) {
		NoteResult result = new NoteResult();
		User  user = userDao.findByName(name);
		if(user==null){
			result.setStatus(1);
			result.setMsg("δ�ҵ����û���");
			return result;
		}
		//��ȡ��Ӻ��ѵ�id
		String friendsId=user.getCn_user_id();
		User my=userDao.findByUserId(userId);
		//�������ӹ��ĺ���
		String allFriends=my.getCn_user_friend();
		if (allFriends != null) {
			String[] friends = allFriends.split("%");
			for (String frien : friends) {
				if (frien.equals(friendsId)) {
					result.setStatus(1);
					result.setMsg("���Ѿ���ӹ��˺���");
					return result;
				}
			}
			allFriends += friendsId;
			allFriends += "%";
            userDao.addFriend(userId,allFriends);
			result.setStatus(0);
			result.setMsg("��Ӻ��ѳɹ���");
			result.setData(user);
			return result;
		}
		allFriends = friendsId + "%";
        userDao.addFriend(userId,allFriends);
		result.setStatus(0);
		result.setMsg("��Ӻ��ѳɹ���");
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
    			result.setMsg("ɾ�����ѳɹ���");
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
		result.setMsg("�����ע������Ϣ�ɹ���");
		result.setData(datas);
		return result;
	}
}
