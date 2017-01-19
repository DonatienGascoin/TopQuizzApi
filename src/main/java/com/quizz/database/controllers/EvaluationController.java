package com.quizz.database.controllers;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.services.AppService;

@Slf4j
@RestController
@RequestMapping(value = "/evaluation")
public class EvaluationController {

	@Autowired
	private AppService appService;

	@RequestMapping("/")
	@ResponseBody
	public ResponseEntity<?> home() {
		return ResponseEntity.badRequest().body("Access denied");
	}

	@RequestMapping(value = "/createEvaluation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> createEvaluation(@RequestParam(name = "evaluatorPseudo") String evaluatorPseudo,
			@RequestParam(name = "targetPseudo") String targetPseudo, @RequestParam(name = "quizzId") Integer quizzId,
			@RequestParam(name = "quizzName") String quizzName, @RequestParam(name = "deadLine") Long timestamp,
			@RequestParam(name = "timer") Integer timer) {
		ReturnObject object = new ReturnObject();
		try {
			// Create date from timestamp
			Date deadLine = new Date(timestamp * 1000);
			object = appService.createEvaluation(evaluatorPseudo, targetPseudo, quizzId, quizzName, deadLine, timer);
		} catch (Exception e) {
			log.error("Impossible to add Statistic [pseudo: " + evaluatorPseudo + ", target: " + targetPseudo
					+ ",quizzId: " + quizzId + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	/**
	 * 
	 * @param evaluatorPseudo
	 * @param targetPseudos
	 *            pseudo|pseudo|pseudo|pseudo...
	 * @param quizzId
	 * @param quizzName
	 * @param timestamp
	 * @param timer
	 * @return
	 */
	@RequestMapping(value = "/createEvaluationsForMultipleUsers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> createEvaluationsForMultipleUsers(
			@RequestParam(name = "evaluatorPseudo") String evaluatorPseudo,
			@RequestParam(name = "targetPseudos") String targetPseudos, @RequestParam(name = "quizzId") Integer quizzId,
			@RequestParam(name = "quizzName") String quizzName, @RequestParam(name = "deadLine") Long timestamp,
			@RequestParam(name = "timer") Integer timer) {
		ReturnObject object = new ReturnObject();
		try {
			// Create date from timestamp
			Date deadLine = new Date(timestamp * 1000);
			object = appService.createEvaluations(evaluatorPseudo, targetPseudos, quizzId, quizzName, deadLine, timer);
		} catch (Exception e) {
			log.error("Impossible to add Statistic [pseudo: " + evaluatorPseudo + ", target: " + targetPseudos
					+ ",quizzId: " + quizzId + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/makeDone", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> makeDone(@RequestParam(name = "targetPseudo") String targetPseudo,
			@RequestParam(name = "evaluationId") Integer id) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.makeDone(targetPseudo, id);
		} catch (Exception e) {
			log.error("Impossible to add Statistic [pseudo: " + targetPseudo + ", evaluationId: " + id + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/getEvaluationsForPseudo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getEvaluationsForPseudo(
			@RequestParam(name = "targetPseudo") String targetPseudo) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.getEvaluationsForPseudo(targetPseudo);
		} catch (Exception e) {
			log.error("Impossible to get evaluation [pseudo: " + targetPseudo + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/getEvaluation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getEvaluation(@RequestParam(name = "targetPseudo") String targetPseudo,
			@RequestParam(name = "quizzId") Integer quizzId) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.getEvaluation(targetPseudo, quizzId);
		} catch (Exception e) {
			log.error("Impossible to get evaluation [pseudo: " + targetPseudo + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/getEvaluationsForEvaluatorPseudo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getEvaluationsStatisticsForEvaluatorPseudo(
			@RequestParam(name = "pseudo") String pseudo) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.getEvaluationsForEvaluatorPseudo(pseudo);
		} catch (Exception e) {
			log.error("Impossible to get evaluation [pseudo: " + pseudo + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/getEvaluationsStatisticsForEvaluatorPseudo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getEvaluationsStatisticsForEvaluatorPseudo(
			@RequestParam(name = "pseudo") String pseudo, @RequestParam(name = "quizzId") Integer quizzId) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.getEvaluationsStatisticsForEvaluatorPseudo(pseudo, quizzId);
		} catch (Exception e) {
			log.error("Impossible to get evaluation [pseudo: " + pseudo + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
}
