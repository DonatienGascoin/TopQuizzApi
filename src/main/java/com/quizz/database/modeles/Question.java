package com.quizz.database.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.beans.QuizzBean;
import com.quizz.database.beans.ResponseBean;
import com.quizz.database.beans.ThemeBean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String label;

	private String pseudo;

	private Collection<Response> responses;

	public Collection<Theme> themes;

	public Collection<Quizz> quizzs;
	
	private String explanation;
	
	public Question(String label, String pseudo, String explanation) {
		int id;
		this.pseudo = pseudo;
		this.label = label;
		this.explanation = explanation;
	}

	public QuestionBean convertToBean() {
		QuestionBean bean = new QuestionBean();

		bean.setId(this.id);
		bean.setLabel(this.label);
		bean.setPseudo(this.pseudo);

		if (CollectionUtils.isNotEmpty(responses)) {
			Collection<ResponseBean> responsesBean = new ArrayList<ResponseBean>();
			for (Response reponse : responses) {
				responsesBean.add(reponse.convertToBean());
			}
			bean.setReponses(responsesBean);
		}

		if (CollectionUtils.isNotEmpty(themes)) {
			Collection<ThemeBean> themesBean = new ArrayList<ThemeBean>();
			for (Theme theme : themes) {
				themesBean.add(theme.convertToBean());
			}
			bean.setTheme(themesBean);
		}

		if (CollectionUtils.isNotEmpty(quizzs)) {
			Collection<QuizzBean> quizzsBean = new ArrayList<QuizzBean>();
			for (Quizz quizz : quizzs) {
				quizzsBean.add(quizz.convertToBean());
			}
			bean.setQuizzs(quizzsBean);
		}


		bean.setExplanation(this.explanation);
		return bean;
	}
}
