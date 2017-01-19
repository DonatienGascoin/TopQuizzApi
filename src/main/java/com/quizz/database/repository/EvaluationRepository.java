package com.quizz.database.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.quizz.database.beans.EvaluationBean;

/**
 * 
 * @author Donatien
 * @version 0.1
 * @since 07/12/2016
 *
 */
@Transactional
@Component
public interface EvaluationRepository extends CrudRepository<EvaluationBean, Integer> {

	List<EvaluationBean> findByTargetPseudoAndDoneAndDeadLineGreaterThan(String targetPseudo, Boolean false1, Date date);

	List<EvaluationBean> findByEvaluatorPseudo(String pseudo);

	EvaluationBean findByTargetPseudoAndDoneAndQuizzId(String targetPseudo, Boolean false1, Integer quizzId);

}
