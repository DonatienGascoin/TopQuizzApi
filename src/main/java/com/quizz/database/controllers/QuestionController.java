package com.quizz.database.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.services.AppService;

@Slf4j
@RestController
@RequestMapping(value = "/question")
public class QuestionController {

	@Autowired
	private AppService appService;
	@RequestMapping("/")
	@ResponseBody
	public ResponseEntity<?> home() {
		return ResponseEntity.badRequest().body("Question");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> addQuestion(
			@RequestParam(name = "label") String label,
			@RequestParam(name = "pseudo") String pseudo,
			@RequestParam(name = "explanation") String explanation) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.addQuestion(label, pseudo, explanation);
		} catch (Exception e) {
			log.error("Impossible to add Question [pseudo: " + pseudo + ", question: " + label + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
}
