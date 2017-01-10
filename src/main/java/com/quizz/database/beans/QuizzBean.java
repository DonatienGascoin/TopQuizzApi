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
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="Quizz")
public class QuizzBean{

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String isVisible;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="Quizz_Question",
				joinColumns=
					@JoinColumn(name="Quizz", referencedColumnName="id"),
				inverseJoinColumns=
					@JoinColumn(name="Question", referencedColumnName="id")
	)
	private Collection<QuestionBean> questions;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="User_Quizz",
		joinColumns=@JoinColumn(name="Quizz", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="User", referencedColumnName="pseudo")
	)
	private Collection<UserBean> sharedUser;

}
