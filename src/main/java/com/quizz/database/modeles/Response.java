package com.quizz.database.modeles;

import java.io.Serializable;

import com.quizz.database.beans.ResponseBean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response implements Serializable {

	/**
	 * Using for serialise object
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String label;
	
	private Boolean isValide;
	
	private int idQuestion; 
	
	public Response(String label, Boolean isValide, int idQuestion) {
		int id;
		this.label = label;
		this.isValide = isValide;
		this.idQuestion = idQuestion;
	}
	
	public ResponseBean convertToBean(){
		ResponseBean bean = new ResponseBean();
		bean.setLabel(this.label);
		bean.setIsValide(this.isValide);
		bean.setIdQuestion(this.idQuestion);
		return bean;
	}
}
