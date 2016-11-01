package com.quizz.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quizz.database.services.AppService;
import com.quizz.database.services.UserService;

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
    public ResponseEntity<?> addUser(@RequestParam(name = "pseudo") String pseudo,
			@RequestParam(name = "mail") String mail,
			@RequestParam(name = "password") String password) {
    	try {
			String result = "";

			appService.addUser(pseudo, mail, password);

			return ResponseEntity.ok().body(result);
		} catch (IllegalArgumentException e) {
			// logger.error("Error during add product [" + productId+
			// "] in basket [" + sessionId + "] with the quantity ["+ quantity +
			// "]", e);
			return ResponseEntity.unprocessableEntity().body(e.getMessage());
		}
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUser(@RequestParam(name = "pseudo") String pseudo) {
    	
        return ResponseEntity.ok("User !");
    }

    @RequestMapping(value = "/getByMail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserByMail(@RequestParam(name = "mail") String mail) {
    	
        return ResponseEntity.ok("User !");
    }
}
