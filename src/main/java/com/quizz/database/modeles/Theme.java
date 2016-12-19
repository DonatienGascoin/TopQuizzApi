package com.quizz.database.modeles;

import java.io.Serializable;

import com.quizz.database.beans.ThemeBean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Theme implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;

	private String name;
	
	private int idQuestion;

	public Theme(String str, int idQuestion) {
		super();
		this.name = str;
		this.idQuestion = idQuestion;
	}

	public Theme(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Theme(int id, String name, int idQuestion) {
		super();
		this.id = id;
		this.name = name;
		this.idQuestion = idQuestion;
	}
	
	public ThemeBean convertToBean(){
		ThemeBean bean = new ThemeBean();
		bean.setId(this.id);
		bean.setName(this.name);
		bean.setIdQuestion(this.idQuestion);
		return bean;
	}

}
