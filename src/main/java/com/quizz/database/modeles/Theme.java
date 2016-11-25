package com.quizz.database.modeles;

import java.io.Serializable;

import com.quizz.database.beans.ThemeBean;

import antlr.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Theme implements Serializable, Comparable<Theme> {
	
	private static final long serialVersionUID = 1L;

	private int id;

	private String name;

	private int idQuestion;
	
	public ThemeBean convertToBean(){
		ThemeBean bean = new ThemeBean();
		bean.setId(this.id);
		bean.setName(this.name);
		bean.setIdQuestion(this.idQuestion);
		
		return bean;
	}

	@Override
	public int compareTo(Theme o) {
		return this.name.compareTo(o.getName());
	}
}
