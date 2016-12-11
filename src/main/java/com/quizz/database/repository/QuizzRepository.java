package com.quizz.database.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.QuizzBean;

/**
 * 
 * @author Romain
 *
 */
@Transactional
@Component
public interface QuizzRepository extends CrudRepository<QuizzBean, Integer>{
	
	List<QuizzBean >findAll();
	
	QuizzBean findByName(String name);
	
}
