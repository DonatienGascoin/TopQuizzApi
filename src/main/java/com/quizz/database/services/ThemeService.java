package com.quizz.database.services;

import com.quizz.database.modeles.ReturnObject;

/**
 * 
 * {@link ThemeService} will validate data and call {@link ThemeRepository} 
 * 
 * @author Louis Paret
 * @version 1.0
 * @since 05/12/2016
 * 
 */

public interface ThemeService {
	public ReturnObject getThemeByName(String name);

	public ReturnObject getAllThemes();

	public ReturnObject addTheme(String name);

	public ReturnObject deleteTheme(int id);
	
	public ReturnObject addThemeWithIdQuestion(String name, int idQuestion);
}
