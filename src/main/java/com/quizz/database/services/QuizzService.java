package com.quizz.database.services;

import java.util.Collection;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.beans.UserBean;
import com.quizz.database.datas.Visibility;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.repository.QuizzRepository;

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

	public ReturnObject getQuizzByName(String name);

	public ReturnObject deleteQuizzById(Integer id);

	public ReturnObject getAllQuizzesByQuestionBean(Collection<QuestionBean> questions);

	public ReturnObject deleteSharedQuizz(Integer quizzId, UserBean quizzReceiver);

	public ReturnObject shareQuizzToUser(Integer quizzId, UserBean quizzReceiver);

	public ReturnObject getReceivedQuizz(int id);
	
}
