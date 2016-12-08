package com.quizz.database.modeles;

import java.io.Serializable;

import com.quizz.database.beans.ResponseTmpBean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseTmp implements Serializable {

	/**
	 * Using for serialise object
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String key; 

	private String label;
	
	private Boolean isValide;
	
	
	public ResponseTmp(int id, String key, String label, Boolean isValide) {
		this.id= id;
		this.key = key;
		this.label = label;
		this.isValide = isValide;
	}
	
	public ResponseTmp(String key, String label, Boolean isValide) {
		this.key = key;
		this.label = label;
		this.isValide = isValide;
	}
	
	public ResponseTmpBean convertToBean(){
		ResponseTmpBean bean = new ResponseTmpBean();
		bean.setId(this.id);
		bean.setKey(this.key);
		bean.setLabel(this.label);
		bean.setIsValide(this.isValide);
		return bean;
	}
}
