package com.quizz.database.services;

import com.quizz.database.beans.User;
import com.quizz.database.services.QuizzService;

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
	
	User getUser(String pseudo);
	
	User getUserByMail(String mail);
	
	User addUser(String pseudo, String mail, String password);
	
	User editUser(String pseudo, String mail, String password);
	
	Boolean deleteUser(String pseudo);
	
	User changePassword(String password, String email);
	
}
