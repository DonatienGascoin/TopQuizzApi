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

	ReturnObject addResponse(String label, Boolean isValide, int idQuestion);
}
