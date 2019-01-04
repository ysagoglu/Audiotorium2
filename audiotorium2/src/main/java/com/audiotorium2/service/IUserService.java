package com.audiotorium2.service;

import com.audiotorium2.entity.User;

public interface IUserService {

	public User signUp(User user) throws Exception;

	public User login(String username, String password) throws Exception;

}
