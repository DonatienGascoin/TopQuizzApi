package com.quizz.database.modeles;

import com.quizz.database.datas.ReturnCode;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReturnObject {
	
	private ReturnCode code;
	
	private Object object;
}
