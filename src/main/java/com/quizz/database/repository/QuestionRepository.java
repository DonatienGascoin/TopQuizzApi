package com.quizz.database.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.QuestionBean;

/**
 * @author Maxence Royer
 */
@Transactional
@Component
public interface QuestionRepository extends CrudRepository<QuestionBean, Integer>{
	QuestionBean findById(Integer id);
}
