package com.quizz.database.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.ResponseTmpBean;
import com.quizz.database.beans.UserBean;

/**
 * 
 * @author Donatien
 *
 */
@Transactional
@Component
public interface UserRepository extends CrudRepository<UserBean, String>{
	
	UserBean findByMail(String mail);
	
	UserBean findByPseudoAndPassword(String pseudo, String password);
	
	List<UserBean> findByPseudoContaining(String pseudo);
}
