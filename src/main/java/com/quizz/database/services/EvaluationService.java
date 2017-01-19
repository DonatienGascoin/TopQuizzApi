package com.quizz.database.services;

import java.util.Date;

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

	public ReturnObject getEvaluationsForPseudo(String targetPseudo);

	public ReturnObject getEvaluationsForEvaluatorPseudo(String pseudo);

	public ReturnObject getEvaluations(String targetPseudo, Integer quizzId);
}
