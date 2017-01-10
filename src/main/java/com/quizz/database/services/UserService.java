package com.quizz.database.services;

import java.util.Collection;

import com.quizz.database.beans.UserBean;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;
import com.quizz.database.repository.UserRepository;

/**
 * 
 * {@link UserService} will validate data and call {@link UserRepository} 
 * 
 * @author Donatien Gascoin
 * @version 1.0
 * @since 31/10/2016
 * 
 */
public interface UserService {
	
	public ReturnObject getUser(String pseudo);
	
	public UserBean getUserBean(String pseudo);
	
	public ReturnObject checkUserCredentials(String pseudo, String password);
	
	public ReturnObject getUserByMail(String mail);

	public ReturnObject getAllUsers();

	public ReturnObject addUser(String pseudo, String mail, String password);

	public ReturnObject editUser(String pseudo, String mail, String password, Boolean active, Collection<User> friends, Collection<Question> questions);

	public ReturnObject deleteUser(String pseudo);
	
	public ReturnObject changePassword(String password, String mail);
	
	public ReturnObject activeUser(String mail);
	
	public ReturnObject getQuestions(String pseudo);

	public ReturnObject searchUserByPartialPseudo(String partialPseudo, String pseudo);
	
	public ReturnObject addFriendbyPseudo (UserBean user, UserBean friendUser);
	
	public ReturnObject deleteFriend(UserBean user, UserBean friendUser);
}
