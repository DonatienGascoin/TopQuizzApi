package com.quizz.database.beans;

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
@Table(name = "Response")
public class ResponseBean {

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String label;

	@Column(nullable = false)
	private Boolean isValide;

	@Column(nullable = false)
	private int idQuestion;

	public ResponseBean(String label, Boolean isValide, int idQuestion) {
		this.label = label;
		this.isValide = isValide;
		this.idQuestion = idQuestion;
	}
}
