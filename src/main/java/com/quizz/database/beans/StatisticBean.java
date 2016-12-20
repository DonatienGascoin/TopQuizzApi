package com.quizz.database.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Statistic")
public class StatisticBean {

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String pseudo;

	@Column(nullable = false)
	private Integer quizzId;

	@Column()
	private Integer nbRightAnswers;
	
	@Column()
	private Integer nbQuestions;
	
	@Column()
	private Date date;

	public StatisticBean(int id, String pseudo, Integer quizzId, Integer nbRightAnswers, Integer nbAnswers, Date date) {
		this.id = id;
		this.pseudo = pseudo;
		this.quizzId = quizzId;
		this.nbRightAnswers = nbRightAnswers;
		this.nbQuestions = nbAnswers;
		this.date = date;
	}

	public StatisticBean(String pseudo, Integer quizzId, Integer nbRightAnswers, Integer nbAnswers, Date date) {
		this.pseudo = pseudo;
		this.quizzId = quizzId;
		this.nbRightAnswers = nbRightAnswers;
		this.nbQuestions = nbAnswers;
		this.date = date;
	}
	
	
}
