package com.quizz.database.beans;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.springframework.context.annotation.Lazy;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="Question")
public class QuestionBean implements Serializable {

	/**
	 * Using for serialise object
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="question_pseudo")
	private String pseudo;

	@Column(nullable = false)
	private String label;
	
	@Column(nullable = false)
	private String explanation;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="idQuestion")
	private Collection<ResponseBean> reponses;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="idQuestion")
	public Collection<ThemeBean> theme;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="Quizz_Question",
		joinColumns=@JoinColumn(name="Question", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="Quizz", referencedColumnName="id")
	)
	public Collection<QuizzBean> quizzs;

}
