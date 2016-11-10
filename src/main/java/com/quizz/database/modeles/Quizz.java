package com.quizz.database.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.beans.QuizzBean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Quizz implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String name;

	private char isVisible;

	public Collection<Question> questions;

	public QuizzBean convertToBean() {
		QuizzBean bean = new QuizzBean();

		bean.setId(this.id);
		bean.setName(this.name);
		bean.setIsVisible(this.isVisible);

		if (CollectionUtils.isNotEmpty(questions)) {
			Collection<QuestionBean> questionsBean = new ArrayList<QuestionBean>();
			for (Question question : questions) {
				question.convertToBean();
			}
			bean.setQuestions(questionsBean);
		}

		return bean;
	}
}
