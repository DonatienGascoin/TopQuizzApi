package com.quizz.database.services.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;
import com.quizz.database.services.AppService;
import com.quizz.database.services.QuestionService;
import com.quizz.database.services.ResponseService;
import com.quizz.database.services.UserService;

@Service
public class AppServiceImpl implements AppService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private ResponseService responseService;

	@Override
	public ReturnObject getUser(String pseudo) {
		return userService.getUser(pseudo);
	}

	@Override
	public ReturnObject addUser(String pseudo, String mail, String password) {
		return userService.addUser(pseudo, mail, password);
	}

	@Override
	public ReturnObject editUser(String pseudo, String mail, String password, Boolean active, Collection<User> friends, Collection<Question> questions) {
		return userService.editUser(pseudo, mail, password, active, friends, questions);
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
	
	@Override
	public ReturnObject checkUserCredentials(String pseudo, String password) {
		return userService.checkUserCredentials(pseudo, password);
	}
	
	@Override
	public ReturnObject activeUser(String mail) {
		return userService.activeUser(mail);
	}
	
	public ReturnObject addResponse(String label, Boolean isValide, int idQuestion) {
		return responseService.addResponse(label, isValide, idQuestion);
	}

	@Override
	public ReturnObject addQuestion(String label, String pseudo, String explanation) {
		return questionService.addQuestion(label, pseudo, explanation);
	}
}
