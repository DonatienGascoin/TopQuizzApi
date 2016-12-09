package com.quizz.database.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.QuizzBean;

/**
 * @author Maxence Royer
 */
@Transactional
@Component
public interface QuizzRepository extends CrudRepository<QuizzBean, Integer> {
	QuizzBean findById(Integer id);
	
	QuizzBean findByName(String name);
}
