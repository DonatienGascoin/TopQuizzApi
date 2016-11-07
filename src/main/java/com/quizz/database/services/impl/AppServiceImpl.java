package com.quizz.database.services.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;
import com.quizz.database.services.AppService;
import com.quizz.database.services.UserService;

@Service
public class AppServiceImpl implements AppService {
	
	@Autowired
	private UserService userService;

	@Override
	public ReturnObject getUser(String pseudo) {
		
		return userService.getUser(pseudo, password);
	}

	@Override
	public ReturnObject addUser(String pseudo, String mail, String password) {
		return userService.addUser(pseudo, mail, password);
	}

	@Override
	public ReturnObject editUser(String pseudo, String mail, String password, Collection<User> friends, Collection<Question> questions) {
		return userService.editUser(pseudo, mail, password, friends, questions);
	}

	@Override
	public ReturnObject deleteUser(String pseudo) {
		return userService.deleteUser(pseudo);
	}

	@Override
	public ReturnObject getUserByMail(String mail) {
		return userService.getUserByMail(mail);
	}

	@Override
	public ReturnObject changePassword(String password, String email) {
		return userService.changePassword(password, email);
	}

}
