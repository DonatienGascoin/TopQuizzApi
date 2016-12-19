package com.quizz.database.services;

import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.datas.Visibility;
import com.quizz.database.modeles.Question;
import java.util.Collection;

/**
 * 
 * {@link QuizzService} will validate data and call {@link QuizzRepository} 
 * 
 * @author Maxence Royer
 * @version 0.1
 * @since 12/11/2016
 * 
 */

public interface QuizzService {
    
    public ReturnObject addQuizz(String name, Visibility visibility, Collection<Question> questions);

	public ReturnObject getAllQuizzesByPseudo(String pseudo);

	public ReturnObject getQuizzByName(String name);

	public ReturnObject deleteQuizzById(Integer id);
}
