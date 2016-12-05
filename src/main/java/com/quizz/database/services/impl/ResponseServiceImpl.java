package com.quizz.database.services.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.ResponseBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.Response;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.repository.ResponseRepository;
import com.quizz.database.services.ResponseService;

@Slf4j
@Service
public class ResponseServiceImpl implements ResponseService{

	@Autowired
	private ResponseRepository responseRepository;
	
	@Override
	public ReturnObject addResponse(String label, Boolean isValide, int idQuestion) {
		log.info("Add response [response: " + label + "]");
		ReturnObject object = new ReturnObject();
		Response response = new Response();
		object.setObject(response);
		ResponseBean r = new ResponseBean(label, isValide, idQuestion);
		try {
			// Save method was automatically managed by CrudRepository
			ResponseBean responseBean = responseRepository.save(r);
			response = new Response(responseBean.getLabel(), responseBean.getIsValide(), responseBean.getIdQuestion());
			object.setCode(ReturnCode.ERROR_000);
			log.info("Response succesfully created");
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("idQuestion not exist in table Question [[response: " + label + "], " + ReturnCode.ERROR_200);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to add Response [response: " + label + "], " + ReturnCode.ERROR_050);
		}
		object.setObject(response);
		return object;
	}
}
