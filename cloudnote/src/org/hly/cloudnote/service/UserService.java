package org.hly.cloudnote.service;

import org.hly.cloudnote.util.NoteResult;

//public NoteResult xxx(�������);
public interface UserService {
	
	public NoteResult registUser(String name,String password,String nick) throws Exception;
	
	public NoteResult checkLogin(String name, String password) throws Exception;
}
