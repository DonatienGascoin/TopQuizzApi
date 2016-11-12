package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Theme;
import com.quizz.database.modeles.User;
import com.quizz.database.services.AppService;
import com.quizz.database.services.QuestionService;
import com.quizz.database.services.QuizzService;
import com.quizz.database.services.ResponseService;
import com.quizz.database.services.UserService;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	private UserService userService;

	@Autowired
	private QuizzService quizzService;
	
	@Autowired
	private QuestionService questionService;

	@Autowired
	private ResponseService responseService;
	
	private static final int LITTLESTRINGLIMIT = 50;
	
	private static final int BIGSTRINGLIMIT = 50;
	
	private static final String SEPARATOR = "|";

	@Override
	public ReturnObject getUser(String pseudo) {
		return userService.getUser(pseudo);
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

	@Override
	public ReturnObject checkUserCredentials(String pseudo, String password) {
		return userService.checkUserCredentials(pseudo, password);
	}

	@Override
	public ReturnObject getAllQuizzesByPseudo(String pseudo) {
		return quizzService.getAllQuizzesByPseudo(pseudo);
	}

	@Override
	public ReturnObject getQuizzByName(String name) {
		return quizzService.getQuizzByName(name);
	}

	@Override
	public ReturnObject activeUser(String mail) {
		return userService.activeUser(mail);
	}

	@Override
	public ReturnObject addTmpResponse(String number, String pseudo, String label, Boolean isValide) {
		ReturnObject obj = new ReturnObject();
		if(StringUtils.isEmpty(number) || StringUtils.isEmpty(pseudo) || StringUtils.isEmpty(label)){
			obj.setCode(ReturnCode.ERROR_150);
			return obj;
		}
		if(label.length() > LITTLESTRINGLIMIT || pseudo.length() > LITTLESTRINGLIMIT ){
			obj.setCode(ReturnCode.ERROR_500);
		}
		obj = userService.getUser(pseudo);
		if (obj.getObject() != null) {
			return responseService.addTmpResponse(pseudo + SEPARATOR + number, label, isValide);
		}
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject addQuestion(String pseudo, String label, String themes, String explanation) {
		ReturnObject obj = new ReturnObject();
		if (StringUtils.isEmpty(pseudo) || StringUtils.isEmpty(label) || StringUtils.isEmpty(themes) || StringUtils.isEmpty(explanation)) {
			obj.setCode(ReturnCode.ERROR_150);
			return obj;
		}
		if(explanation.length() > BIGSTRINGLIMIT || label.length() > LITTLESTRINGLIMIT){
			obj.setCode(ReturnCode.ERROR_500);
		}
		obj = userService.getUser(pseudo);
		if (obj.getObject() != null) {
			Collection<Theme> t = new ArrayList<Theme>();
			String[] split = StringUtils.split(themes, SEPARATOR);
			for(String str: split){
				t.add(new Theme(Integer.parseInt(str)));
			}
			//Add question
			 obj = questionService.addQuestion(pseudo, label, t, explanation);
			 
			 if(ReturnCode.ERROR_000.equals(obj.getCode()) && obj.getObject() != null){
				 responseService.linkTmpResponse(((Question)obj.getObject()).getId(), pseudo);
			 }
		}
		obj.setCode(ReturnCode.ERROR_100);
				
		return obj;
	}
}
