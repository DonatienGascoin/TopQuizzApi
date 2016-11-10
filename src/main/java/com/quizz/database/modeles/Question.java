package com.quizz.database.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;

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

	private Collection<Response> reponses;

	public Collection<Theme> themes;

	public Collection<Quizz> quizzs;

	public QuestionBean convertToBean() {
		QuestionBean bean = new QuestionBean();

		bean.setId(this.id);
		bean.setLabel(this.label);
		bean.setPseudo(this.pseudo);

		Collection<ResponseBean> responsesBean = new ArrayList<ResponseBean>();
		for (Response reponse : reponses) {
			responsesBean.add(reponse.convertToBean());
		}
		bean.setReponses(responsesBean);

		Collection<ThemeBean> themesBean = new ArrayList<ThemeBean>();
		for (Theme theme : themes) {
			themesBean.add(theme.convertToBean());
		}
		bean.setReponses(responsesBean);

		Collection<QuizzBean> quizzsBean = new ArrayList<QuizzBean>();
		for (Quizz quizz : quizzs) {
			quizzsBean.add(quizz.convertToBean());
		}

		bean.setQuizzs(quizzsBean);

		return bean;
	}
}
