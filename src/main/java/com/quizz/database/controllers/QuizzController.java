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
import com.quizz.database.datas.Visibility;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.services.AppService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/quizz")
public class QuizzController {

	@Autowired
	private AppService appService;

	@RequestMapping("/")
	@ResponseBody
	public ResponseEntity<?> home() {
		return ResponseEntity.badRequest().body("Access denied");
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> addQuizz(@RequestParam(name = "name") String name,
			@RequestParam(name = "visibility") String visibility, @RequestParam(name = "questions") String questions) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.addQuizz(name, Visibility.valueOf(visibility), questions);
		} catch (Exception e) {
			log.error("Impossible to add Quizz [name: " + name + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/getAllQuizzesByPseudo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getAllQuizzesByPseudo(@RequestParam(name = "pseudo") String pseudo) {
		ReturnObject object = null;
		try {
			object = appService.getAllQuizzesByPseudo(pseudo);
			if (object == null) {
				object = new ReturnObject();
				object.setCode(ReturnCode.ERROR_050);
			}
		} catch (Exception e) {
			log.error("Impossible to get all quizzes for user [pseudo: " + pseudo + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/getOwnQuizzesByPseudo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getOwnQuizzesByPseudo(@RequestParam(name = "pseudo") String pseudo) {
		ReturnObject object = null;
		try {
			object = appService.getOwnQuizzesByPseudo(pseudo);
			if (object == null) {
				object = new ReturnObject();
				object.setCode(ReturnCode.ERROR_050);
			}
		} catch (Exception e) {
			log.error("Impossible to get all quizzes for user [pseudo: " + pseudo + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/getQuizzByName", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getQuizzByName(@RequestParam(name = "name") String name) {
		ReturnObject object = null;
		try {
			object = appService.getQuizzByName(name);
			if (object == null) {
				object = new ReturnObject();
				object.setCode(ReturnCode.ERROR_050);
			}
		} catch (Exception e) {
			log.error("Impossible to get quizz by name [name: " + name + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/deleteQuizzById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> deleteQuizzById(@RequestParam(name = "id") Integer id) {
		ReturnObject object = null;
		try {
			object = appService.deleteQuizzById(id);
			if (object == null) {
				object = new ReturnObject();
				object.setCode(ReturnCode.ERROR_050);
			}
		} catch (Exception e) {
			log.error("Impossible to delete quizz by id [integer: " + id + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/shareQuizz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> shareQuizz(@RequestParam(name = "quizzId") Integer quizzId,
			@RequestParam(name = "userSharedPseudo") String userSharedPseudo) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.addShareQuizz(quizzId, userSharedPseudo);
		} catch (Exception e) {
			log.error("Impossible to share quizz [quizzId: " + quizzId + ",name: " + userSharedPseudo + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}

	@RequestMapping(value = "/deleteSharedQuizz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> deleteSharedQuizz(@RequestParam(name = "quizzId") Integer quizzId,
			@RequestParam(name = "userSharedPseudo") String userSharedPseudo) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.deleteSharedQuizz(quizzId, userSharedPseudo);
		} catch (Exception e) {
			log.error("Impossible to delete shared quizz [quizzId: " + quizzId + ",name: " + userSharedPseudo + "]", e);
			object.setCode(ReturnCode.ERROR_100);
		}
		return ResponseEntity.ok().body(object);
	}

}
