package com.quizz.database.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.ResponseBean;

/**
 * 
 * @author Louis
 *
 */
@Transactional
@Component
public interface ResponseRepository extends CrudRepository<ResponseBean, Integer>{

}
