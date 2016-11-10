package com.quizz.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quizz.database.beans.UserBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.services.AppService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private AppService appService;

	@RequestMapping("/")
	@ResponseBody
	public ResponseEntity<?> home() {
		return ResponseEntity.badRequest().body("Access denied");
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> addUser(@RequestParam(name = "pseudo") String pseudo,
			@RequestParam(name = "mail") String mail, @RequestParam(name = "password") String password) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.addUser(pseudo, mail, password);
		} catch (Exception e) {
			log.error("Impossible to add User [pseudo: " + pseudo + ", mail: " + mail + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getUser(@RequestParam(name = "pseudo") String pseudo) {
		ReturnObject object = null;
		try {
			object = appService.getUser(pseudo);
		} catch (Exception e) {
			log.error("Impossible to get User [pseudo: " + pseudo + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/getByMail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getUserByMail(@RequestParam(name = "mail") String mail) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.getUserByMail(mail);
		} catch (Exception e) {
			log.error("Impossible to get User [mail: " + mail + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> changePassword(@RequestParam(name = "password") String password, @RequestParam(name = "email") String email) {
		ReturnObject object = null;
		try {
			object = appService.changePassword(password, email);
		} catch (Exception e) {
			log.error("Impossible to change password User [mail: " + email + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
	
}
