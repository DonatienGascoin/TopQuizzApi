package com.quizz.database.controllers;


import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/friends")
public class FriendsController {

	
	@Autowired
	private AppService appService;
	
	@RequestMapping("/")
	@ResponseBody
	public ResponseEntity<?> home() {
		return ResponseEntity.badRequest().body("Access denied");
	}
	
	
	@RequestMapping(value = "/getAllFriendsByPseudo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getAllFriendsByPseudo(@RequestParam(name = "pseudo") String pseudo) {
		ReturnObject object = null;
		try {
			object = appService.getAllFriendsByPseudo(pseudo);
		} catch (Exception e) {
			log.error("Impossible to get all friends [pseudo: " + pseudo + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
	
}

