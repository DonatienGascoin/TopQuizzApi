package com.quizz.database.beans;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

	@Id
	private int id;
	
	@Column(nullable = false)
	private String label;
	
	@Column(nullable = false)
	private Theme theme;
		
	@Column(nullable = false)
	@OneToMany( targetEntity=Quizz.class )
	private Collection<Reponse> reponses;

}
