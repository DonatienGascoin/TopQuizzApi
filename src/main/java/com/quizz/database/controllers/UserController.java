package com.quizz.database.controllers;

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
	public ResponseEntity<ReturnObject> changePassword(@RequestParam(name = "password") String password,
			@RequestParam(name = "mail") String mail) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.changePassword(password, mail);
		} catch (Exception e) {
			log.error("Impossible to change password User [mail: " + mail + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/checkCredentials", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> checkCredentials(@RequestParam(name = "pseudo") String pseudo,
			@RequestParam(name = "password") String password) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.checkUserCredentials(pseudo, password);
		} catch (Exception e) {
			log.error("Impossible to get User [pseudo: " + pseudo + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
	
	@RequestMapping(value = "/activeUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> activeUser(@RequestParam(name = "mail") String mail) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.activeUser(mail);
		} catch (Exception e) {
			log.error("Impossible to active User [mail: " + mail + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
	
	@RequestMapping(value = "/checkActive", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> checkActive(@RequestParam(name = "pseudo") String pseudo) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.checkActive(pseudo);
		} catch (Exception e) {
			log.error("Impossible to know if user is active [pseudo: " + pseudo + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

}
