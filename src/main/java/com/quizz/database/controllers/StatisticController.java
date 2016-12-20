package com.quizz.database.controllers;

import lombok.extern.slf4j.Slf4j;

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
@RequestMapping(value = "/statistic")
public class StatisticController {

	@Autowired
	private AppService appService;

	@RequestMapping("/")
	@ResponseBody
	public ResponseEntity<?> home() {
		return ResponseEntity.badRequest().body("Access denied");
	}

	@RequestMapping(value = "/getTenLastScoreForQuizz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> addResponse(@RequestParam(name = "pseudo") String pseudo, @RequestParam(name = "quizzId") Integer quizzId) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.getTenLastScoreForQuizz(pseudo, quizzId);
		} catch (Exception e) {
			log.error("Impossible to get Statistic for quizz [pseudo: " + pseudo + ", quizz id: " + quizzId + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/addScoreForQuizz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> addTmpResponse(@RequestParam(name = "pseudo") String pseudo, @RequestParam(name = "quizzId") Integer quizzId, @RequestParam(name = "quizzName") String quizzName,
			@RequestParam(name = "nbRightAnswers") Integer nbRightAnswers, @RequestParam(name = "nbQuestion") Integer nbQuestion) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.addScoreForQuizz(pseudo, quizzId, quizzName, nbRightAnswers, nbQuestion);
		} catch (Exception e) {
			log.error("Impossible to add Statistic [pseudo: " + pseudo + ", quizzId: " + quizzId + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
}
