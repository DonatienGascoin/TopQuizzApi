package com.quizz.database.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.ResponseTmpBean;

/**
 * 
 * @author Donatien
 * @version 0.1
 * @since 07/12/2016
 *
 */
@Transactional
@Component
public interface ResponseTmpRepository extends CrudRepository<ResponseTmpBean, Integer>{
	public List<ResponseTmpBean> findByKeyContaining(String key);

}
