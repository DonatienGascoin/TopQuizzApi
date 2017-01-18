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
@Table(name = "Evaluation")
public class EvaluationBean {

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private Integer quizzId;

	@Column(nullable = false)
	private String evaluatorPseudo;

	@Column(nullable = false)
	private String targetPseudo;

	@Column(nullable = false, name = "quizz_name")
	private String quizzName;

	@Column(nullable = false)
	private Date deadLine;

	@Column(nullable = false)
	private Integer timer;

	@Column(nullable = false)
	private Boolean done;

	public EvaluationBean(int id, Integer quizzId, String evaluatorPseudo, String targerPseudo, String quizzName,
			Date deadLine, Integer timer, Boolean done) {
		super();
		this.id = id;
		this.quizzId = quizzId;
		this.evaluatorPseudo = evaluatorPseudo;
		this.targetPseudo = targerPseudo;
		this.quizzName = quizzName;
		this.deadLine = deadLine;
		this.timer = timer;
		this.done = done;
	}

	public EvaluationBean(Integer quizzId, String evaluatorPseudo, String targerPseudo, String quizzName, Date deadLine,
			Integer timer, Boolean done) {
		super();
		this.quizzId = quizzId;
		this.evaluatorPseudo = evaluatorPseudo;
		this.targetPseudo = targerPseudo;
		this.quizzName = quizzName;
		this.deadLine = deadLine;
		this.timer = timer;
		this.done = done;
	}

	

}