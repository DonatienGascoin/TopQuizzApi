package com.quizz.database.services.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;
import com.quizz.database.services.AppService;
import com.quizz.database.services.ThemeService;
import com.quizz.database.services.UserService;

import antlr.StringUtils;

@Service
public class AppServiceImpl implements AppService {
	

	@Autowired
	private UserService userService;

	@Autowired
	private ThemeService themeService;

	@Override
	public ReturnObject getUser(String pseudo) {
		return userService.getUser(pseudo);
	}

	@Override
	public ReturnObject addUser(String pseudo, String mail, String password) {
		return userService.addUser(pseudo, mail, password);
	}

	@Override
	public ReturnObject editUser(String pseudo, String mail, String password, Boolean active, Collection<User> friends,
			Collection<Question> questions) {
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
	public ReturnObject getAllThemesByUser(String pseudo) {
		ReturnObject obj = userService.getUser(pseudo);
		if (obj.getObject() != null) {
			return themeService.getAllThemesByUser((Collection<Question>) ((User) obj.getObject()).getQuestions());
		}
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject changePassword(String password, String email) {
		return userService.changePassword(password, email);
	}

	public ReturnObject checkUserCredentials(String pseudo, String password) {
		return userService.checkUserCredentials(pseudo, password);
	}

	public ReturnObject activeUser(String mail) {
		return userService.activeUser(mail);
	}
}
