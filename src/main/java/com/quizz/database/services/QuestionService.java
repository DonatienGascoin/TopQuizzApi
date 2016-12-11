/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quizz.database.services;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Question;
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
    
    public Question getQuestionByQuestionBean(QuestionBean bean);
    
    public ReturnObject findById(Integer id);
    
	public ReturnObject addQuestion(String pseudo, String label, String explanation);
}
