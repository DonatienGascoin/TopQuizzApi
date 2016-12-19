package com.quizz.database.controllers;

import com.quizz.database.datas.ReturnCode;

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

/**
 *
 * @author Romain
 */

@Slf4j
@RestController
@RequestMapping(value = "/theme")
public class ThemeController {
    
    @Autowired
    private AppService appService;
    
    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity<?> home(){
        return ResponseEntity.badRequest().body("Access denied");
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReturnObject> addTheme(@RequestParam(name = "name") String name){
        ReturnObject object = new ReturnObject();
        try {
            object = appService.addTheme(name);
        }catch (Exception e){
            log.error("Impossible to add Theme [name: " + name +"]", e);
        }
        return ResponseEntity.ok().body(object);
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReturnObject> deleteTheme(@RequestParam int id){
        ReturnObject object = new ReturnObject();
        try {
            object = appService.deleteTheme(id);
        }catch (Exception e) {
            log.error("Impossible to delete ThemeException [id: " + id + "]", e);
            object.setCode(ReturnCode.ERROR_100);
        }
        return ResponseEntity.ok().body(object);
    }
    
    @RequestMapping(value = "/getByName", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReturnObject> getThemeByName(@RequestParam(name = "name") String name){
        ReturnObject object = new ReturnObject();
        try {
            object = appService.getThemeByName(name);
        }catch (Exception e) {
            log.error("Impossible to get Theme [name: " + name + "]", e);
        }
        return ResponseEntity.ok().body(object);
    }
    
    @RequestMapping(value = "/getAllThemes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getAllThemes() {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.getAllThemes();
		} catch (Exception e) {
			log.error("Impossible to get Themes", e);
		}
		return ResponseEntity.ok().body(object);
	}
	
	@RequestMapping(value = "/getAllByUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getAllThemesByUser(@RequestParam(name = "pseudo") String pseudo) {
		ReturnObject object = new ReturnObject();
		try {
			object = appService.getAllThemesByUser(pseudo);
		} catch (Exception e) {
			log.error("Impossible to get all Theme for user [" + pseudo + "]");
		}
		return ResponseEntity.ok().body(object);
	}
	
	@RequestMapping(value = "/getQuestionsByThemes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReturnObject> getQuestionsByName(@RequestParam(name = "theme") String theme, @RequestParam(name = "pseudo") String pseudo) {
		ReturnObject object = null;
		try {
			object = appService.getQuestionsByThemes(theme, pseudo);
		} catch (Exception e) {
			log.error("Impossible to get all questions for theme [theme: " + theme + "]", e);
		}
		return ResponseEntity.ok().body(object);
	}
}
