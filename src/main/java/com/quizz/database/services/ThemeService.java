package com.quizz.database.services;

import java.util.Collection;

import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;

public interface ThemeService {
	public ReturnObject getAllThemesByUser(Collection<Question> questions);
}
