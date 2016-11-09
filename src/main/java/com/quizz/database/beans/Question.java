package com.quizz.database.beans;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
	@GeneratedValue
	private int id;
	
	@ManyToMany(mappedBy="questions")
	private Collection<Quizz> Quizzs;

	@Column(nullable = false)
	private String label;

	@OneToMany
	@JoinColumn(name="idQuestion")
	private Collection<Response> reponses;

	@OneToMany
	@JoinColumn(name="idQuestion")
	public Collection<Theme> theme;

}
