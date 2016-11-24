package com.quizz.database.services;

import java.util.Collection;

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
public interface ThemeService {
	
	public ReturnObject getThemeByName(String name);

	public ReturnObject getAllTheme();

	public ReturnObject addTheme(String name);

	public ReturnObject deleteTheme(String name);
	
}
