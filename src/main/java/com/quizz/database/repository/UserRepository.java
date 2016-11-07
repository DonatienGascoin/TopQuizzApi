package com.quizz.database.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.UserBean;

/**
 * 
 * @author Donatien
 *
 */
@Transactional
@Component
public interface UserRepository extends CrudRepository<UserBean, String>{
	
	List<UserBean >findAll();
	
	UserBean findByMail(String mail);
	
	User findByPseudoAndPassword(String pseudo, String password);
	
}
