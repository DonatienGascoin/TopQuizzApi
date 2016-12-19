/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quizz.database.repository;

import com.quizz.database.beans.QuestionBean;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Maxence Royer
 * @author Romain
 * 
 */
public interface QuestionRepository extends CrudRepository<QuestionBean, Integer>{
	QuestionBean findById(Integer id);
}
