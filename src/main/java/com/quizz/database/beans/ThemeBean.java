package com.quizz.database.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Theme")
public class ThemeBean {

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String name;
}
