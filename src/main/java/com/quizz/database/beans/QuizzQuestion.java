package com.quizz.database.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class QuizzQuestion {
	
	@Id
	private int id;
	
	@Column(nullable = false)
	private int idQuizz;
	
	@Column(nullable = false)
	private int idQuestion;
}
