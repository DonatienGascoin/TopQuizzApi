package com.quizz.database.services;

import java.util.Collection;

import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;
import com.quizz.database.repository.UserRepository;

/**
 * 
 * {@link ThemeService} will validate data and call {@link ThemeRepository} 
 * 
 * @author Romain Chazottier
 * @version 1.0
 * @since 25/11/2016
 * 
 */
public interface ThemeService {
	
	public ReturnObject getThemeByName(String name);

	public ReturnObject getAllTheme();

	public ReturnObject addTheme(String name);

	public ReturnObject deleteTheme(int id);
	
}
