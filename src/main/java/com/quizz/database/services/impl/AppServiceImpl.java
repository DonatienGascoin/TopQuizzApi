package com.quizz.database.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.quizz.database.beans.User;
import com.quizz.database.services.AppService;
import com.quizz.database.services.UserService;


public class AppServiceImpl implements AppService {
	
	@Autowired
	private UserService userService;

	@Override
	public User getUser(String pseudo) {
		
		return userService.getUser(pseudo);
	}

	@Override
	public User addUser(String pseudo, String mail, String password) {
		return userService.addUser(pseudo, mail, password);
	}

	@Override
	public User editUser(String pseudo, String mail, String password) {
		return userService.editUser(pseudo, mail, password);
	}

	@Override
	public Boolean deleteUser(String pseudo) {
		return userService.deleteUser(pseudo);
	}

}
