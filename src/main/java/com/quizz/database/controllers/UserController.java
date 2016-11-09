package com.quizz.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quizz.database.beans.User;
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
			object.setUser(appService.addUser(pseudo, mail, password));
			if(object.getUser() != null){
				log.info("Add user [pseudo: " + pseudo + ", mail: " + mail + "]");
				object.setCode(ReturnCode.ERROR_000);
				return ResponseEntity.ok().body(object);
			}
			log.error("Impossible to add User [pseudo: " + pseudo + ", mail: " + mail + "]");
			return ResponseEntity.unprocessableEntity().body("Impossible to create user in DB");
		} catch (IllegalArgumentException e) {
			log.error("Impossible to add User [pseudo: " + pseudo + ", mail: " + mail + "]", e);
			return ResponseEntity.unprocessableEntity().body(e.getMessage());
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getUser(@RequestParam(name = "pseudo") String pseudo) {
		try {
			User user = appService.getUser(pseudo);
			if (user != null) {
				log.info("Get User [pseudo: " + pseudo + "]");
				return ResponseEntity.ok(user);
			}
			log.error("User not found [pseudo: " + pseudo + "]");
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			log.error("Impossible to get User [pseudo: " + pseudo + "]", e);
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "/getByMail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getUserByMail(@RequestParam(name = "mail") String mail) {
		try {
			User user = appService.getUserByMail(mail);
			if (user != null) {
				log.info("Get User [mail: " + mail + "]");
				return ResponseEntity.ok(user);
			}
			log.error("User not found [mail: " + mail + "]");
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			log.error("Impossible to get User [mail: " + mail + "]", e);
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> changePassword(@RequestParam(name = "password") String password, @RequestParam(name = "email") String email) {
		try {
			User user = appService.getUserByMail(email);
			if (user != null) {
				log.info("Get User [mail: " + email + "]");
				log.info("New password [password: " + password + "]");
				user = appService.changePassword(password, email);
				return ResponseEntity.ok(user);
			}
			log.error("User not found [mail: " + email + "]");
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			log.error("Impossible to get User [mail: " + email + "]", e);
			return ResponseEntity.notFound().build();
		}
	}
	
}
