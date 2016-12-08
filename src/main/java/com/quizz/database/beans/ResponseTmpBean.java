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
@Table(name = "ResponseTmp")
public class ResponseTmpBean {

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false, name="responseKey")
	private String key;

	@Column(nullable = false)
	private String label;

	@Column(nullable = false)
	private Boolean isValide;

	public ResponseTmpBean(String key, String label, Boolean isValide) {
		this.key = key;
		this.label = label;
		this.isValide = isValide;
	}
	
	public ResponseBean convertToResponseBean(int idQuestion){
		return new ResponseBean(label, isValide, idQuestion);
	}
}
