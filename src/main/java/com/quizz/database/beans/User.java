package com.quizz.database.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class User implements Serializable {

	/**
	 * Using for serialise object
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column()
	private String pseudo;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String mail;

	@Column
	@ManyToMany
	@JoinTable(name = "Friend", joinColumns = @JoinColumn(name = "Pseudo", referencedColumnName = "pseudo"), inverseJoinColumns = @JoinColumn(name = "Pseudo2", referencedColumnName = "pseudo"))
	private java.util.Collection<User> friends;

	@Column
	@OneToMany
	@JoinColumn
	private java.util.Collection<Question> question;

	public User(String pseudo, String password, String mail) {
		this.pseudo = pseudo;
		this.password = password;
		this.mail = mail;
	}

}
