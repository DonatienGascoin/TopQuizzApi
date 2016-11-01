package com.quizz.database.services;

import java.util.List;

import com.quizz.database.beans.User;
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
	
	public User getUser(String pseudo);
	
	public User getUserByMail(String mail);

	public List<User> getAllUsers();

	public User addUser(String pseudo, String mail, String password);

	public User editUser(String pseudo, String mail, String password);

	boolean deleteUser(String pseudo);
}
