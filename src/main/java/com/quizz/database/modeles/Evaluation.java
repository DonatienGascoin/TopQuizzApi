package com.quizz.database.modeles;

import java.io.Serializable;
import java.util.Date;

import com.quizz.database.beans.EvaluationBean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Evaluation implements Serializable {

	/**
	 * Using for serialise object
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String evaluatorPseudo;

	private String targetPseudo;

	private Integer quizzId;

	private String quizzName;

	private Integer timer;

	private Date deadLine;

	private Boolean done;

	public Evaluation(Integer id, String evaluatorPseudo, String targerPseudo, Integer quizzId, String quizzName,
			Integer timer, Date deadLine, Boolean done) {
		super();
		this.id = id;
		this.evaluatorPseudo = evaluatorPseudo;
		this.targetPseudo = targerPseudo;
		this.quizzId = quizzId;
		this.quizzName = quizzName;
		this.timer = timer;
		this.deadLine = deadLine;
		this.done = done;
	}

	public Evaluation(String evaluatorPseudo, String targerPseudo, Integer quizzId, String quizzName, Integer timer,
			Date deadLine, Boolean done) {
		super();
		this.evaluatorPseudo = evaluatorPseudo;
		this.targetPseudo = targerPseudo;
		this.quizzId = quizzId;
		this.quizzName = quizzName;
		this.timer = timer;
		this.deadLine = deadLine;
		this.done = done;
	}

	public EvaluationBean convertToBean() {
		if (id != null) {
			return new EvaluationBean(quizzId, evaluatorPseudo, targetPseudo, quizzName, deadLine, timer, done);
		} else {
			return new EvaluationBean(quizzId, evaluatorPseudo, targetPseudo, quizzName, deadLine, timer, done);
		}
	}
}
