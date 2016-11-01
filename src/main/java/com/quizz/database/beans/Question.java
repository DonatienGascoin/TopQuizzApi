package com.quizz.database.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Question implements Serializable {
	
	/**
	 * Using for serialise object
	 */
	private static final long serialVersionUID = 1L;

	private int questionId;
	
	private String label;
	
	private Theme theme;
	
	private List<Reponse> reponses;

}
