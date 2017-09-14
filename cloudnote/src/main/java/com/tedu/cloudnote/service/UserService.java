package com.tedu.cloudnote.service;

import com.tedu.cloudnote.util.NoteResult;

public interface UserService {

	public NoteResult checkLogin(String name,String password);
	
	//返回值约定是NoteResult
	public NoteResult addUser(String name,String nick,String password);
	
	public NoteResult checkPwd(String last_password,String userId);
	
	public NoteResult updatePassword(String new_password,String userId);
	
}
