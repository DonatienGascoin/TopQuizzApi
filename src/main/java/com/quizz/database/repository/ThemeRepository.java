package com.quizz.database.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.Theme;

@Transactional
@Component
public interface ThemeRepository extends CrudRepository<Theme, String>{

	List<Theme>findAll();
	
}

