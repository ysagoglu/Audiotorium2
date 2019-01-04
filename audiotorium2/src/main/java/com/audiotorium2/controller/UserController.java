package com.audiotorium2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.audiotorium2.entity.User;
import com.audiotorium2.service.IUserService;;

@RestController
@RequestMapping("/")
public class UserController {

	@Autowired
	IUserService userService;

	@RequestMapping(value = "/signup", produces = "application/json", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User signUp(@RequestBody User user) throws Exception {

		try {
			return userService.signUp(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	@PostMapping(value = "/login", produces = "application/json")
	@ResponseBody
	public User login(@RequestParam("username") String username, @RequestParam("password") String password)
			throws Exception {

		try {
			return userService.login(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}
