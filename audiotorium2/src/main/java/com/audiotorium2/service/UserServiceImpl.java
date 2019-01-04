package com.audiotorium2.service;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.audiotorium2.dao.IUserDAO;
import com.audiotorium2.entity.User;
import com.audiotorium2.utility.CommonUtility;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserDAO userDAO;
	private final static String KEY = "4815162342";

	@Override
	public User signUp(User user) throws Exception {

		if (userDAO.findByUsername(user.getUsername(), null) != null) {
			throw new Exception("User already registered.");
		}

		if (userDAO.findByEmail(user.getEmail(), null) != null) {
			throw new Exception("User already registered.");
		}

		if (user.getPassword().length() < 8 || user.getPassword().length() > 20) {
			throw new Exception("Password lengt must be at least 8 characters and at most 20 characters.");
		}
		if (user.getUsername().contains("@")) {
			throw new Exception("Username cannot contain '@' character.");
		}

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.MINUTE, 0);
		if (user.getBirthDate().compareTo(today.getTime()) >= 0) {
			throw new Exception("Please enter valid date for birthdate");
		}

		passwordControl(user.getPassword());

		user.setPassword(CommonUtility.encrypt(user.getPassword(), KEY));
		userDAO.saveUser(user);

		insertLog(user.getId(), "signup");
		return user;
	}

	@Override
	public User login(String username, String password) throws Exception {

		boolean email = false;
		if (username.contains("@")) {
			email = true;
		}
		User user = null;
		if (email) {
			user = userDAO.findByEmail(username, CommonUtility.encrypt(password, KEY));
		} else {
			user = userDAO.findByUsername(username, CommonUtility.encrypt(password, KEY));
		}

		if (user == null) {
			throw new Exception("User is not found.");
		}
		insertLog(user.getId(), "login");
		return user;

	}

	private void passwordControl(String password) throws Exception {

		if (!password.matches(".*\\d+.*")) {
			throw new Exception("Password should have at least one digits");
		}

		if (password.equals(password.toUpperCase())) {
			throw new Exception("Password should have at least one lower case character.");
		}

		if (password.equals(password.toLowerCase())) {
			throw new Exception("Password should have at least one upper case character.");
		}

		Pattern patternForSpecialChar = Pattern.compile("[a-zA-Z0-9]*");
		Matcher matcher = patternForSpecialChar.matcher(password);

		if (!matcher.matches()) {
			throw new Exception("Password should not contain special character.");
		}

	}

	public void insertLog(int userId, String operation) throws Exception {
		userDAO.insertLog(operation, userId);
	}

}
