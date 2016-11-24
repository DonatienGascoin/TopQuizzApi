package com.quizz.database.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.ThemeBean;

/**
 * 
 * @author Donatien
 *
 */
@Transactional
@Component
public interface ThemeRepository extends CrudRepository<ThemeBean, String>{
	
	List<ThemeBean >findAll();
	
	ThemeBean findByName(String name);
	
}
