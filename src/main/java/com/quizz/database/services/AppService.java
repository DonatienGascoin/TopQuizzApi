package com.quizz.database.services;

import java.util.Collection;

import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.Quizz;
import com.quizz.database.modeles.Response;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Theme;
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
	
	public ReturnObject getUser(String pseudo);
	
	public ReturnObject getUserByMail(String mail);
	
	public ReturnObject addUser(String pseudo, String mail, String password);
	
	public ReturnObject editUser(String pseudo, String mail, String password, Boolean active, Collection<User> friends, Collection<Question> questions);
	
	public ReturnObject deleteUser(String pseudo);
	
	public ReturnObject changePassword(String password, String email);
	
	public ReturnObject checkUserCredentials(String pseudo, String password);
	
	public ReturnObject activeUser(String mail);
	
	public ReturnObject addQuestion(String label, String pseudo, String explanation);
	
	public ReturnObject addResponse(String label, Boolean isValide, int idQuestion);
}
