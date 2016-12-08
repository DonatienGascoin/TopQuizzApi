/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quizz.database.services.impl;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.beans.UserBean;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;
import com.quizz.database.repository.QuestionRepository;
import com.quizz.database.services.QuestionService;
import java.util.ArrayList;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author romai
 */
@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService{
    
   @Autowired
   private QuestionRepository questionRepository;
    
    /**
	 * Convert QuestionBean to Question
	 * 
	 * @param bean
	 * @return {@link User}
	 */
    @Override
    public Question getQuestionByQuestionBean(QuestionBean bean) {
            Question question = new Question();
            if(bean != null){	
                    question.setId(bean.getId());
                    question.setLabel(bean.getLabel());
            }

            return question;
    }

    @Override
    public ReturnObject findById(Integer id) {
       ReturnObject obj = new ReturnObject();
       try{
            obj.setObject(questionRepository.findById(id));
       }catch(Exception e){
           // TODO
       }
        
        return obj;
    }
    
}
