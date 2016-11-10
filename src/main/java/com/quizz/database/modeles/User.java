package com.quizz.database.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.beans.UserBean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pseudo;

	private String password;

	private String mail;

	private java.util.Collection<User> friends;

	private java.util.Collection<Question> questions;

	public User(String pseudo, String password, String mail, Collection<User> friends, Collection<Question> questions) {
		this.pseudo = pseudo;
		this.password = password;
		this.mail = mail;
		this.friends = friends;
		this.questions = questions;
	}

	public UserBean convertToBean() {
		UserBean bean = new UserBean();
		bean.setPseudo(this.pseudo);
		bean.setPassword(this.password);
		bean.setMail(this.mail);

		if (CollectionUtils.isNotEmpty(friends)) {
			Collection<UserBean> friendsBean = new ArrayList<UserBean>();
			for (User user : this.friends) {
				friendsBean.add(user.convertToBean());
			}
			bean.setFriends(friendsBean);
		}

		if (CollectionUtils.isNotEmpty(questions)) {
			Collection<QuestionBean> questionsBean = new ArrayList<QuestionBean>();
			for (Question question : this.questions) {
				questionsBean.add(question.convertToBean());
			}
			bean.setQuestion(questionsBean);
		}

		return bean;
	}
}
