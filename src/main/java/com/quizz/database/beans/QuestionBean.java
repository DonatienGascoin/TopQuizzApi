package com.quizz.database.beans;

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
public class QuestionBean{

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
	private Collection<ResponseBean> responses;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="idQuestion")
	public Collection<ThemeBean> themes;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="Quizz_Question",
		joinColumns=@JoinColumn(name="Question", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="Quizz", referencedColumnName="id")
	)
	public Collection<QuizzBean> quizzs;

        public QuestionBean(String pseudo, String label, String explanation, Collection<ResponseBean> responses, Collection<ThemeBean> themes, Collection<QuizzBean> quizzs) {
		this.pseudo = pseudo;
		this.label = label;
		this.explanation = explanation;
		this.responses = responses;
		this.themes = themes; 
		this.quizzs = quizzs;
	}
}
