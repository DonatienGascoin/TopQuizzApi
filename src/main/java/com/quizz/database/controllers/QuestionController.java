package com.quizz.database.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.services.AppService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/question")
public class QuestionController {

	@Autowired
	private AppService appService;

	@RequestMapping("/")
	@ResponseBody
	public ResponseEntity<?> home() {
		return ResponseEntity.badRequest().body("Access denied");
	}
	/**
	 * 
	 * @param pseudo
	 * @param label
	 * @param themes Format: id1|id2|id3...
	 * @param explanation maximum size: 250 
	 * @return {@link ReturnObject}
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> addQuestion(
			@RequestParam(name = "pseudo") String pseudo,
			@RequestParam(name = "label") String label,
			@RequestParam(name = "themes") String themes,
			@RequestParam(name = "explanation") String explanation) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.addQuestion(pseudo, label, themes, explanation);
		} catch (Exception e) {
			log.error("Impossible to add Question [pseudo: " + pseudo + ", question: " + label + "]", e);
			if(object.getCode() != null){
				object.setCode(ReturnCode.ERROR_050);
			}
		}
		return ResponseEntity.ok().body(object);
	}
	
	@RequestMapping(value = "/getAllQuestionsByTheme", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getAllQuestionsByTheme(@RequestParam(name = "theme") String theme) {
		ReturnObject object = null;
		try {
			object = appService.getAllQuestionsByTheme(theme);
		} catch (Exception e) {
			log.error("Impossible to get all questions for theme [theme: " + theme + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
}
