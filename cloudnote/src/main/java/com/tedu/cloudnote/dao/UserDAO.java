package com.tedu.cloudnote.dao;

import com.tedu.cloudnote.entity.User;

public interface UserDAO {
	
	public User findByName(String name);

	public void save(User user);
	
	public String findByUserId(String userId);
	
	public void updatePwd(User user);
}
