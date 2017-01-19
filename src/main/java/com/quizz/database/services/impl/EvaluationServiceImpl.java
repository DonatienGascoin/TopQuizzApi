package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.EvaluationBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.Evaluation;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.repository.EvaluationRepository;
import com.quizz.database.services.EvaluationService;

import lombok.Data;
import lombok.NoArgsConstructor;
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
			evaluation.setTargetPseudo(bean.getTargetPseudo());
			evaluation.setQuizzId(bean.getQuizzId());
			evaluation.setQuizzName(bean.getQuizzName());
			evaluation.setTimer(bean.getTimer());
			evaluation.setDeadLine(bean.getDeadLine());
			evaluation.setDone(bean.getDone());
		}
		return evaluation;
	}

	/**
	 * Class used to compare evaluations
	 * 
	 * @author Donatien
	 *
	 */
	@Data
	@NoArgsConstructor
	private class EvaluationIn implements Comparable<EvaluationIn> {
		private String evaluatorPseudo;
		private Date deadLine;

		public EvaluationIn(String evaluatorPseudo, Date deadLine) {
			super();
			this.evaluatorPseudo = evaluatorPseudo;
			this.deadLine = deadLine;
		}

		@Override
		public int compareTo(EvaluationIn o) {
			if (evaluatorPseudo.equals(o.getEvaluatorPseudo()) && deadLine.equals(o.getDeadLine())) {
				return 0;
			}
			return -1;
		}

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
	public ReturnObject getEvaluationsForPseudo(String targetPseudo) {
		log.info("Get evaluations for  [pseudo: " + targetPseudo + "]");
		ReturnObject obj = new ReturnObject();
		List<Integer> result = new ArrayList<Integer>();
		try {
			List<EvaluationBean> list = evaluationRepository
					.findByTargetPseudoAndDoneAndDeadLineGreaterThan(targetPseudo, Boolean.FALSE, new Date());

			for (EvaluationBean bean : list) {
				result.add(bean.getQuizzId());
			}
			obj.setCode(ReturnCode.ERROR_000);
			obj.setObject(result);
		} catch (RuntimeException e) {
			log.error("Impossible to get evaluation [pseudo: " + targetPseudo + "], " + ReturnCode.ERROR_050, e);
		} catch (Exception e) {
			log.error("Impossible to get evaluation [pseudo: " + targetPseudo + "], " + ReturnCode.ERROR_050, e);
		}
		return obj;
	}

	@Override
	public ReturnObject getEvaluationsForEvaluatorPseudo(String pseudo) {
		log.info("Get evaluations for evaluator [pseudo: " + pseudo + "]");
		ReturnObject obj = new ReturnObject();

		try {
			List<EvaluationBean> list = evaluationRepository.findByEvaluatorPseudo(pseudo);

			List<Evaluation> result = new ArrayList<Evaluation>();
			Set<EvaluationIn> dateAlreadyIn = new TreeSet<EvaluationIn>();
			for (EvaluationBean bean : list) {
				EvaluationIn tmp = new EvaluationIn(bean.getEvaluatorPseudo(), bean.getDeadLine());
				if (!dateAlreadyIn.contains(tmp)) {
					dateAlreadyIn.add(tmp);
					result.add(getEvaluationByEvaluationBean(bean));
				}
			}
			obj.setObject(result);
			obj.setCode(ReturnCode.ERROR_000);
		} catch (RuntimeException e) {
			log.error("Impossible to get evaluation for evaluator [pseudo: " + pseudo + "], " + ReturnCode.ERROR_050,
					e);
		} catch (Exception e) {
			log.error("Impossible to get evaluation for evaluator [pseudo: " + pseudo + "], " + ReturnCode.ERROR_050,
					e);
		}
		return obj;
	}

	@Override
	public ReturnObject getEvaluations(String targetPseudo, Integer quizzId) {
		log.info("Get evaluations for  [pseudo: " + targetPseudo + "]");
		ReturnObject obj = new ReturnObject();
		try {
			EvaluationBean bean = evaluationRepository.findByTargetPseudoAndDoneAndQuizzId(targetPseudo, Boolean.FALSE,
					quizzId);

			if (bean != null) {
				obj.setObject(getEvaluationByEvaluationBean(bean));
				obj.setCode(ReturnCode.ERROR_000);
			} else {
				obj.setCode(ReturnCode.ERROR_100);
				log.error("Evaluation not found [pseudo: " + targetPseudo + ", quizzId: " + quizzId + "]");
			}

		} catch (RuntimeException e) {
			log.error("Impossible to get evaluation [pseudo: " + targetPseudo + "], " + ReturnCode.ERROR_050, e);
		} catch (Exception e) {
			log.error("Impossible to get evaluation [pseudo: " + targetPseudo + "], " + ReturnCode.ERROR_050, e);
		}
		return obj;
	}

}
