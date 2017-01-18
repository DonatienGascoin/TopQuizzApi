package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.EvaluationBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.Evaluation;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.repository.EvaluationRepository;
import com.quizz.database.services.EvaluationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvaluationServiceImpl implements EvaluationService {

	@Autowired
	private EvaluationRepository evaluationRepository;

	private Evaluation getEvaluationByEvaluationBean(EvaluationBean bean) {
		Evaluation evaluation = new Evaluation();
		if (bean != null) {
			evaluation.setId(bean.getId());
			evaluation.setEvaluatorPseudo(bean.getEvaluatorPseudo());
			evaluation.setTargerPseudo(bean.getTargetPseudo());
			evaluation.setQuizzId(bean.getQuizzId());
			evaluation.setQuizzName(bean.getQuizzName());
			evaluation.setTimer(bean.getTimer());
			evaluation.setDeadLine(bean.getDeadLine());
			evaluation.setDone(bean.getDone());
		}
		return evaluation;
	}

	@Override
	public ReturnObject createEvaluation(String evaluatorPseudo, String targetPseudo, Integer quizzId, String quizzName,
			Date deadLine, Integer timer) {
		log.info("Add avaluation for  [pseudo: " + targetPseudo + "quizz Id: " + quizzId
				+ "] with parameters [deadline: " + deadLine + ", timer: " + timer + "s]");

		ReturnObject object = new ReturnObject();

		// The theme does not exist
		try {
			EvaluationBean evaluationBean = new EvaluationBean(quizzId, evaluatorPseudo, targetPseudo, quizzName,
					deadLine, timer, Boolean.FALSE);

			evaluationBean = evaluationRepository.save(evaluationBean);
			if (evaluationBean != null) {
				object.setObject(getEvaluationByEvaluationBean(evaluationBean));
				object.setCode(ReturnCode.ERROR_000);
				log.info("Evaluation successfully added");
			} else {
				object.setCode(ReturnCode.ERROR_050);
			}

		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to add evaluation [pseudo: " + targetPseudo + ",quizzId: " + quizzId + "], "
					+ ReturnCode.ERROR_500, e);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to add evaluation [pseudo: " + targetPseudo + ",quizzId: " + quizzId + "], "
					+ ReturnCode.ERROR_200, e);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to add evaluation [pseudo: " + targetPseudo + ",quizzId: " + quizzId + "], "
					+ ReturnCode.ERROR_050, e);
		}
		return object;
	}

	@Override
	public ReturnObject makeDone(String targetPseudo, Integer id) {
		log.info("Make evaluation done for  [pseudo: " + targetPseudo + "quizz Id: " + id + "s]");

		ReturnObject object = new ReturnObject();

		// The theme does not exist
		try {
			EvaluationBean evaluationBean = evaluationRepository.findOne(id);
			evaluationBean.setDone(Boolean.TRUE);

			evaluationBean = evaluationRepository.save(evaluationBean);

			if (evaluationBean != null) {
				object.setObject(getEvaluationByEvaluationBean(evaluationBean));
				object.setCode(ReturnCode.ERROR_000);
				log.info("Evaluation successfully done");
			} else {
				object.setCode(ReturnCode.ERROR_050);
			}

		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to add evaluation [pseudo: " + targetPseudo + ",quizzId: " + id + "], "
					+ ReturnCode.ERROR_500, e);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to add evaluation [pseudo: " + targetPseudo + ",quizzId: " + id + "], "
					+ ReturnCode.ERROR_200, e);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to add evaluation [pseudo: " + targetPseudo + ",quizzId: " + id + "], "
					+ ReturnCode.ERROR_050, e);
		}
		return object;
	}

	@Override
	public List<Integer> getEvaluationsForPseudo(String targetPseudo) {
		log.info("Get evaluations for  [pseudo: " + targetPseudo + "]");

		List<Integer> result = new ArrayList<Integer>();
		try {
			List<EvaluationBean> list = evaluationRepository.findByTargetPseudoAndDoneAndDeadLineGreaterThan(targetPseudo,
					Boolean.FALSE, new Date());// OrderByDeadLine
			
			for (EvaluationBean bean : list) {
				result.add(bean.getQuizzId());
			}
			
		} catch (RuntimeException e) {
			log.error("Impossible to get evaluation [pseudo: " + targetPseudo + "], " + ReturnCode.ERROR_050);
		} catch (Exception e) {
			log.error("Impossible to get evaluation [pseudo: " + targetPseudo + "], " + ReturnCode.ERROR_050);
		}
		return result;
	}

}
