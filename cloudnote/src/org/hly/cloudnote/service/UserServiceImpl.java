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
// ɨ��
@Transactional
public class UserServiceImpl implements UserService {
	@Resource
	// ע��
	private UserDao userDao;
	@Transactional(readOnly=true,rollbackFor=IOException.class,propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)//chelogin��һ�����壬�д����ȡ��  ֻ������   ��ѯһ�����ֻ��   
	 //ֻ�������쳣�Żع���Ҳ����rollbakcFor ָ���׳��ķ�����ʱ�쳣,propagation�����������,isolation�Ǹ���ļ���
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
        //��½�ɹ��� ����½id����data
		result.setData(user.getCn_user_id());
		return result;
	}

	@Override
	public NoteResult registUser(String name, String password, String nick)
			throws Exception {
		NoteResult result = new NoteResult();
		User user = new User();
		// �û���Ψһ�Լ��
         if(userDao.findByName(name)!=null){
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
		//ģ����һ���������쳣
//   		String s=null;s.length();
		return result;
	}

}
