package com.quizz.database.services;

import java.util.Date;
import java.util.List;

import com.quizz.database.modeles.ReturnObject;

/**
 * 
 * @author Donatien Gascoin
 * @version 1.0
 * @since 17/01/2017
 * 
 */
public interface EvaluationService {

	public ReturnObject createEvaluation(String evaluatorPseudo, String targetPseudo, Integer quizzId, String quizzName,
			Date deadLine, Integer timer);

	public ReturnObject makeDone(String targetPseudo, Integer id);

	/**
	 * Waning: this method does not return {@link ReturnObject}, so never use it
	 * for return http response !
	 * 
	 * @param targetPseudo
	 * @return {@link List}
	 */
	public List<Integer> getEvaluationsForPseudo(String targetPseudo);
}
