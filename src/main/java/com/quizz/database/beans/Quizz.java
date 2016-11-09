package com.quizz.database.beans;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="Quizz")
public class Quizz implements Serializable {

	/**
	 * Using for serialise object
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private char isVisible;

	@ManyToMany
	@JoinTable(
		name="Quizz_Question",
				joinColumns=
					@JoinColumn(name="Quizz", referencedColumnName="id"),
				inverseJoinColumns=
					@JoinColumn(name="Question", referencedColumnName="id")
	)
	public Collection<Question> questions;

}
