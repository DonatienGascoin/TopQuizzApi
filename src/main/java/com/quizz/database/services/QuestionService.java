/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quizz.database.services;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Question;
import org.springframework.stereotype.Service;

/**
 *
 * @author Romain
 */
public interface QuestionService {
    
    public Question getQuestionByQuestionBean(QuestionBean bean);
    
    public ReturnObject findById(Integer id);
    
}
