/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quizz.database.services;

import java.util.Collection;

import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;

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

	public ReturnObject getAllThemes();

	public ReturnObject addTheme(String name);

	public ReturnObject deleteTheme(int id);

	public ReturnObject deleteTheme(int id, String name);
	
	public ReturnObject addThemeWithIdQuestion(String name, int idQuestion);
	
	public ReturnObject getAllThemesByUser(Collection<Question> questions);
	
	public ReturnObject getQuestionsByThemes(String theme, String pseudo);
}
