package com.quizz.database.beans;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Reponse implements Serializable {

	/**
	 * Using for serialise object
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String label;
	
	private Boolean isValide;

}
