package com.quizz.database.services;

import java.util.Collection;

import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Theme;
import com.quizz.database.repository.UserRepository;

/**
 * 
 * {@link QuestionService} will validate data and call {@link UserRepository} 
 * 
 * @author Louis Paret
 * @version 1.0
 * @since 05/12/2016
 * 
 */

public interface QuestionService {
	
	public ReturnObject addQuestion(String pseudo, String label, String explanation);
}
