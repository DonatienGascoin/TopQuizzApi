package com.quizz.database.controllers;

import com.quizz.database.datas.Visibility;
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
@RequestMapping(value = "/quizz")
public class QuizzController {
    
    @Autowired
    private AppService appService;
    
    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity<?> home(){
        return ResponseEntity.badRequest().body("Access denied");
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReturnObject> addQuizz(@RequestParam(name = "name") String name, @RequestParam(name = "visibility") String visibility, @RequestParam(name = "questions") String questions){
        ReturnObject object = new ReturnObject();
        try {
            object = appService.addQuizz(name, Visibility.valueOf(visibility), questions);
        }catch (Exception e){
            log.error("Impossible to add Quizz [name: " + name +"]", e);
        }
        return ResponseEntity.ok().body(object);
    }
    
}
