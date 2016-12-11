package com.quizz.database.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "User")
public class UserBean {

	@Id
	@Column()
	private String pseudo;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String mail;

	@Column(nullable = false)
	private Boolean active;

	@Column
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Friend", joinColumns = @JoinColumn(name = "Pseudo", referencedColumnName = "pseudo"), inverseJoinColumns = @JoinColumn(name = "Pseudo2", referencedColumnName = "pseudo"))
	private java.util.Collection<UserBean> friends;

	@Column
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn
	private java.util.Collection<QuestionBean> question;

	public UserBean(String pseudo, String password, String mail, Boolean active) {
		this.pseudo = pseudo;
		this.password = password;
		this.mail = mail;
		this.active = active;
	}

}
