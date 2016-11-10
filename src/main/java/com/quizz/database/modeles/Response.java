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
	
	public ResponseBean convertToBean(){
		ResponseBean bean = new ResponseBean();
		bean.setId(this.id);
		bean.setLabel(this.label);
		bean.setIsValide(this.isValide);
		
		return bean;
	}
}
