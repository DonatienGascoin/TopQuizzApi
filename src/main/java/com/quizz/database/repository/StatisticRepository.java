package com.quizz.database.repository;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.StatisticBean;

/**
 * 
 * @author Donatien
 * @version 0.1
 * @since 07/12/2016
 *
 */
@Transactional
@Component
public interface StatisticRepository extends CrudRepository<StatisticBean, Integer>{
	public Collection<StatisticBean> findTop10ByPseudoAndAndQuizzIdOrderByDateAsc(String pseudo, int quizzId);

}
