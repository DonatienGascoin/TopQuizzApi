package com.quizz.database.services;

import com.quizz.database.modeles.ReturnObject;

/**
 * 
 * {@link ResponseService} will validate data and call {@link ResponseRepository} 
 * 
 * @author Louis Paret
 * @version 1.0
 * @since 05/12/2016
 * 
 */
public interface ResponseService {

	public ReturnObject addTmpResponse(String key, String label, Boolean isValide);

	public ReturnObject linkTmpResponse(int idQuestion, String pseudo);
}
