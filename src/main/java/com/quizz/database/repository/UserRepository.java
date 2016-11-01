package com.quizz.database.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.User;

/**
 * 
 * @author Donatien
 *
 */
@Transactional
@Component
public interface UserRepository extends CrudRepository<User, String>{
	
	List<User >findAll();
	
	User findByMail(String mail);
	
}
