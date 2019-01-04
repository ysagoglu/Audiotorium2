package com.audiotorium2.dao;

import com.audiotorium2.entity.User;

public interface IUserDAO {
	public String saveUser(User user);

	public User findByUsername(String username, String password);

	public User findByEmail(String email, String password);

	public void insertLog(String operation, int userId);
}
