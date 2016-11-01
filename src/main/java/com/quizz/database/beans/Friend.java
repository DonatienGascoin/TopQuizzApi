package com.quizz.database.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Friend implements Serializable {
	
	/**
	 * Using for serialise object
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	@Column(nullable = false)
	private User user1;
	
	@Column(nullable = false)
	private User user2;
	
	

}
