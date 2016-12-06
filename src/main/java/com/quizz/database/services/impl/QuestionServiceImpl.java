package com.quizz.database.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.services.QuestionService;
import com.quizz.database.repository.QuestionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Override
	public ReturnObject addQuestion(String label, String pseudo, String explanation) {
		log.info("Add question [pseudo: " + pseudo + ", question: " + label + "]");
		ReturnObject object = new ReturnObject();
		Question question = new Question();
		object.setObject(question);
		QuestionBean q = new QuestionBean(label, pseudo, explanation);
		try {
			// Save method was automatically managed by CrudRepository
			QuestionBean questionBean = questionRepository.save(q);
			question = new Question(questionBean.getLabel(), questionBean.getPseudo(), questionBean.getExplanation());
			object.setCode(ReturnCode.ERROR_000);
			log.info("Question successfully added");
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Pseudo not exist in the table USER [pseudo: " + pseudo + ", question: " + question + "], " + ReturnCode.ERROR_200);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to add Question [pseudo: " + pseudo + ", question: " + question + "], " + ReturnCode.ERROR_050);
		}
		object.setObject(question);
		return object;
	}
}
