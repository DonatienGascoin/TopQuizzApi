package com.quizz.database.services;

import java.util.Collection;
import java.util.Date;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.datas.Visibility;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;

/**
 * 
 * This class make transition between different service: Ex: {@link AppService}
 * will check in {@link UserService} if the User exist, and take all associated
 * quizz in {@link AppService}
 * 
 * @author Donatien Gascoin
 * @version 1.0
 * @since 31/10/2016
 * 
 * 
 */
public interface AppService {

	public ReturnObject getUser(String pseudo);

	public ReturnObject getUserByMail(String mail);

	public ReturnObject addUser(String pseudo, String mail, String password);

	public ReturnObject editUser(String pseudo, String mail, String password, Boolean active, Collection<User> friends,
			Collection<Question> questions);

	public ReturnObject deleteUser(String pseudo);

	public ReturnObject changePassword(String password, String email);

	public ReturnObject getAllThemesByUser(String pseudo);

	public ReturnObject checkUserCredentials(String pseudo, String password);

	public ReturnObject addQuizz(String name, Visibility visibility, String questions);

	public Question getQuestionByQuestionBean(QuestionBean bean);

	public ReturnObject activeUser(String mail);

	public ReturnObject getAllQuizzesByPseudo(String pseudo);
	
	public ReturnObject getOwnQuizzesByPseudo(String pseudo);

	public ReturnObject deleteQuizzById(Integer id);

	public ReturnObject getQuizzByName(String name);

	public ReturnObject addQuestion(String pseudo, String label, String themes, String explanation);

	public ReturnObject addTmpResponse(String number, String pseudo, String label, Boolean isValide);

	public ReturnObject getAllThemes();

	public ReturnObject getQuestionsByThemes(String theme, String pseudo);

	public ReturnObject addTheme(String name);

	public ReturnObject deleteTheme(int id);

	public ReturnObject getThemeByName(String name);

	public ReturnObject getTenLastScoreForQuizz(String pseudo, Integer quizzId);

	public ReturnObject addScoreForQuizz(String pseudo, Integer quizzId, String quizzName, Integer nbRightAnswers,
			Integer nbQuestions);
	
	public ReturnObject addShareQuizz(Integer quizzId, String userSharedPseudo);

	public ReturnObject deleteSharedQuizz(Integer quizzId, String userSharedPseudo);

	public ReturnObject getAllFriendsByPseudo(String pseudo);

	public ReturnObject addFriendbyPseudo(String pseudo, String friendPseudo);

	public ReturnObject deleteFriend(String pseudo, String friendPseudo);

	public ReturnObject searchUserByPartialPseudo(String partialPseudo, String pseudo);

	public ReturnObject createEvaluation(String evaluatorPseudo, String targetPseudo, Integer quizzId, String quizzName, Date deadLine,
			Integer timer);

	public ReturnObject makeDone(String targetPseudo, Integer id);

	public ReturnObject getEvaluationsForPseudo(String targetPseudo);

	public ReturnObject getEvaluationsForEvaluatorPseudo(String pseudo);

	public ReturnObject createEvaluations(String evaluatorPseudo, String targetPseudos, Integer quizzId,
			String quizzName, Date deadLine, Integer timer);
}
