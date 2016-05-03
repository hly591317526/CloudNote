package org.hly.cloudnote.service;

import org.hly.cloudnote.util.NoteResult;

//public NoteResult xxx(ÇëÇó²ÎÊý);
public interface UserService {
	
	public NoteResult registUser(String name,String password,String nick) throws Exception;
	
	public NoteResult checkLogin(String name, String password) throws Exception;

	public NoteResult changePwd(String userId,String lastpwd, String newpwd) throws Exception;

}
