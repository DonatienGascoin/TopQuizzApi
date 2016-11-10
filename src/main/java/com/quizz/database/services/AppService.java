package com.quizz.database.services;

import java.util.Collection;

import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;

/**
 * 
 * This class make transition between different service:
 * Ex: {@link AppService} will check in {@link UserService} if the User exist, and take all associated quizz in {@link QuizzService}
 * 
 * @author Donatien Gascoin
 * @version 1.0
 * @since 31/10/2016
 * 

 */
public interface AppService {
	
	ReturnObject getUser(String pseudo);
	
	ReturnObject getUserByMail(String mail);
	
	ReturnObject addUser(String pseudo, String mail, String password);
	
	ReturnObject editUser(String pseudo, String mail, String password, Collection<User> friends, Collection<Question> questions);
	
	ReturnObject deleteUser(String pseudo);
	
	ReturnObject changePassword(String password, String email);
	
}
