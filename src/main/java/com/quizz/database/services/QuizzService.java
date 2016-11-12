package com.quizz.database.services;

import com.quizz.database.modeles.ReturnObject;

/**
 * 
 * {@link QuizzService} will validate data and call {@link QuizzRepository} 
 * 
 * @author Maxence Royer
 * @version 0.1
 * @since 12/11/2016
 * 
 */
public interface QuizzService {

	public ReturnObject getAllQuizzesByPseudo(String pseudo);

	public ReturnObject getQuizzByName(String name);
}
