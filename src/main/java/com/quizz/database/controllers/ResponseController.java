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
@RequestMapping(value = "/response")
public class ResponseController {

	@Autowired
	private AppService appService;
	@RequestMapping("/")
	@ResponseBody
	public ResponseEntity<?> home() {
		return ResponseEntity.badRequest().body("Response");
	}
	/*
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> addResponse(
			@RequestParam(name = "pseudo") String pseudo,
			@RequestParam(name = "label") String label,
			@RequestParam(name = "isValide") Boolean isValide,
			@RequestParam(name = "idQuestion") int idQuestion) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.addResponse(pseudo, label, isValide, idQuestion);
		} catch (Exception e) {
			log.error("Impossible to add Response [response: " + label + ", valide: " + isValide + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
	*/
	/**
	 * 
	 * @param key: composed by: userId + question number (Ex: UserId: 1, question n°1: key = 11)
	 * @param pseudo
	 * @param label
	 * @param isValide
	 * @return {@link ReturnObject}
	 */
	@RequestMapping(value = "/addTmpResponse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> addTmpResponse(
			@RequestParam(name = "number") String number,
			@RequestParam(name = "pseudo") String pseudo,
			@RequestParam(name = "label") String label,
			@RequestParam(name = "isValide") Boolean isValide) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.addTmpResponse(number, pseudo, label, isValide);
		} catch (Exception e) {
			log.error("Impossible to add Response [response: " + label + ", valide: " + isValide + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
}
