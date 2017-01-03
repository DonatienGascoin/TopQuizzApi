package com.quizz.database.modeles;

import java.io.Serializable;
import java.util.Date;

import com.quizz.database.beans.StatisticBean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Statistic implements Serializable, Comparable<Statistic> {

	/**
	 * Using for serialise object
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private String pseudo;

	private Integer quizzId;

	private String quizzName;

	private Integer nbRightAnswers;

	private Integer nbQuestions;

	private Date date;

	public Statistic(int id, String pseudo, Integer quizzId, Integer rightAnswers, Integer nbAnswers, Date date) {
		super();
		this.id = id;
		this.pseudo = pseudo;
		this.quizzId = quizzId;
		this.nbRightAnswers = rightAnswers;
		this.nbQuestions = nbAnswers;
		this.date = date;
	}

	public StatisticBean convertToBean() {
		return new StatisticBean(this.id, this.pseudo, this.quizzId, this.quizzName, this.nbRightAnswers,
				this.nbQuestions, this.date);
	}

	@Override
	public int compareTo(Statistic o) {
		if (this.getId() == o.getId()) {
			return 0;
		} else if (this.getId() > o.getId()) {
			return 1;
		}
		return -1;
	}
}
