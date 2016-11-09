package com.quizz.database.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quizz.database.beans.Theme;
import com.quizz.database.beans.User;
import com.quizz.database.services.AppService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/theme")
public class ThemeController {
	
	@Autowired
	private AppService appService;
	
	@RequestMapping("/")
	@ResponseBody
	public ResponseEntity<?> home() {
		return ResponseEntity.badRequest().body("Access denied");
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getAllThemes() {
		try {
			List<Theme> listThemes = appService.getAllThemes();
			
			if (listThemes != null && listThemes.size()!=0) {
				log.info("Get All Themes [Theme Element Number: " + listThemes.size() + "]");
				return ResponseEntity.ok(listThemes);
			}
			log.error("No Theme in database.");
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			log.error("Problem when calling getAllThemes() ", e);
			return ResponseEntity.notFound().build();
		}
	}

}
