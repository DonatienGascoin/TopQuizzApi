package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.beans.UserBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.datas.Visibility;
import com.quizz.database.modeles.Evaluation;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.Quizz;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Statistic;
import com.quizz.database.modeles.User;
import com.quizz.database.services.AppService;
import com.quizz.database.services.EvaluationService;
import com.quizz.database.services.QuestionService;
import com.quizz.database.services.QuizzService;
import com.quizz.database.services.ResponseService;
import com.quizz.database.services.StatisticService;
import com.quizz.database.services.ThemeService;
import com.quizz.database.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppServiceImpl implements AppService {

	private static final String SEPARATOR_QUIZZ = ",";

	private static final int LITTLESTRINGLIMIT = 50;

	private static final int BIGSTRINGLIMIT = 50;

	private static final String SEPARATOR = "|";

	@Autowired
	private UserService userService;

	@Autowired
	private ThemeService themeService;

	@Autowired
	private QuizzService quizzService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ResponseService responseService;

	@Autowired
	private StatisticService statisticService;

	@Autowired
	private EvaluationService evaluationService;

	@Override
	public ReturnObject getUser(String pseudo) {
		return userService.getUser(pseudo);
	}

	@Override
	public ReturnObject addUser(String pseudo, String mail, String password) {
		return userService.addUser(pseudo, mail, password);
	}

	@Override
	public ReturnObject editUser(String pseudo, String mail, String password, Boolean active, Collection<User> friends,
			Collection<Question> questions) {
		return userService.editUser(pseudo, mail, password, active, friends, questions);
	}

	@Override
	public ReturnObject deleteUser(String pseudo) {
		return userService.deleteUser(pseudo);
	}

	@Override
	public ReturnObject getUserByMail(String mail) {
		return userService.getUserByMail(mail);
	}

	@Override
	public ReturnObject getAllThemesByUser(String pseudo) {
		log.info(" get All Themes By User. [pseudo" + pseudo + "] ");
		ReturnObject obj = userService.getUser(pseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			return themeService.getAllThemesByUser((Collection<Question>) ((User) obj.getObject()).getQuestions());
		}
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject changePassword(String password, String email) {
		return userService.changePassword(password, email);
	}

	@Override
	public ReturnObject checkUserCredentials(String pseudo, String password) {
		return userService.checkUserCredentials(pseudo, password);
	}

	@Override
	public ReturnObject getAllQuizzesByPseudo(String pseudo) {
		log.info(" get All Quizzes By Pseudo. [pseudo: " + pseudo + "] ");
		ReturnObject obj = userService.getUser(pseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			User tmp = (User) obj.getObject();
			// Get own quizzs
			List<Quizz> lQuizz = new ArrayList<Quizz>();

			// Test if User have any quizz shared with him
			if (CollectionUtils.isNotEmpty(tmp.getReiceivedQuizz())) {
				for (Quizz q : tmp.getReiceivedQuizz()) {
					ReturnObject quizzByName = quizzService.getReceivedQuizz(q.getId());
					if (ReturnCode.ERROR_000.equals(quizzByName.getCode())) {
						Quizz quizz = (Quizz) quizzByName.getObject();
						lQuizz.add(quizz);
					} else {
						log.error("Impossible to get ReceivedQuizz from User [pseudo: " + pseudo + "]");
					}
				}
			}

			ReturnObject ownQuizzesByPseudo = getOwnQuizzesByPseudo(pseudo);
			if (ReturnCode.ERROR_000.equals(ownQuizzesByPseudo.getCode())) {
				lQuizz.addAll((Collection<Quizz>) ownQuizzesByPseudo.getObject());
			}

			obj.setObject(lQuizz);
			return obj;
		}

		// User not found
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject getQuizzByName(String name) {
		return quizzService.getQuizzByName(name);
	}

	@Override
	public ReturnObject getQuestionsByThemes(String theme, String pseudo) {
		return themeService.getQuestionsByThemes(theme, pseudo);
	}

	public ReturnObject activeUser(String mail) {
		return userService.activeUser(mail);
	}

	public ReturnObject getAllThemes() {
		return themeService.getAllThemes();
	}

	@Override
	public ReturnObject addTheme(String name) {
		return themeService.addTheme(name);
	}

	@Override
	public ReturnObject deleteTheme(int id) {
		return themeService.deleteTheme(id);
	}

	@Override
	public ReturnObject getThemeByName(String name) {
		return themeService.getThemeByName(name);
	}

	@Override
	public Question getQuestionByQuestionBean(QuestionBean bean) {
		return questionService.getQuestionByQuestionBean(bean);
	}

	@Override
	public ReturnObject addQuizz(String name, Visibility visibility, String questions) {
		log.info(" Add quizz. [name : " + name + " visibility : " + visibility + " questions :" + questions + "] ");

		String[] split = StringUtils.split(questions, SEPARATOR_QUIZZ);
		Collection<Question> questionList = new ArrayList<Question>();

		for (String split1 : split) {
			ReturnObject obj = questionService.findById(Integer.parseInt(split1));
			QuestionBean test = (QuestionBean) obj.getObject();
			Question test2 = getQuestionByQuestionBean(test);
			questionList.add(test2);
		}

		return quizzService.addQuizz(name, visibility, questionList);
	}

	@Override
	public ReturnObject deleteQuizzById(Integer id) {
		return quizzService.deleteQuizzById(id);
	}

	@Override
	public ReturnObject addTmpResponse(String number, String pseudo, String label, Boolean isValide) {
		ReturnObject obj = new ReturnObject();
		if (StringUtils.isEmpty(number) || StringUtils.isEmpty(pseudo) || StringUtils.isEmpty(label)) {
			obj.setCode(ReturnCode.ERROR_150);
			return obj;
		}
		if (label.length() > LITTLESTRINGLIMIT || pseudo.length() > LITTLESTRINGLIMIT) {
			obj.setCode(ReturnCode.ERROR_500);
		}
		obj = userService.getUser(pseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			return responseService.addTmpResponse(pseudo + SEPARATOR + number, label, isValide);
		}
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject addQuestion(String pseudo, String label, String themes, String explanation) {
		log.info("Add uestion [pseudo: " + pseudo + ", label: " + label + ", themes: " + themes + ", explanation: "
				+ explanation + "]");
		ReturnObject obj = new ReturnObject();
		if (StringUtils.isEmpty(pseudo) || StringUtils.isEmpty(label) || StringUtils.isEmpty(themes)
				|| StringUtils.isEmpty(explanation)) {
			obj.setCode(ReturnCode.ERROR_150);
			return obj;
		}
		if (explanation.length() > BIGSTRINGLIMIT || label.length() > LITTLESTRINGLIMIT) {
			obj.setCode(ReturnCode.ERROR_500);
			return obj;
		}
		obj = userService.getUser(pseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			// Add question
			obj = questionService.addQuestion(pseudo, label, explanation);

			if (!ReturnCode.ERROR_000.equals(obj.getCode())) {
				log.error("Impossible to add Question, stop add");
				return obj;
			}
			Question q = (Question) obj.getObject();
			// Add themes
			if (ReturnCode.ERROR_000.equals(obj.getCode()) && obj.getObject() != null) {
				String[] split = StringUtils.split(themes, SEPARATOR);
				for (String str : split) {
					obj = themeService.addThemeWithIdQuestion(str, q.getId());
					if (!ReturnCode.ERROR_000.equals(obj.getCode())) {
						log.error("Error during addTheme, impossible to add question");
						questionService.deleteQuestion(q.getId());
						return obj;
					}
				}
			}

			// Add responses
			if (ReturnCode.ERROR_000.equals(obj.getCode()) && obj.getObject() != null) {
				log.info("Link tmp Response to response");
				obj = responseService.linkTmpResponse(q.getId(), pseudo);
				if (!ReturnCode.ERROR_000.equals(obj.getCode())) {
					log.error("Error during link tmpResponse to Question, impossible to add Question");
					String[] split = StringUtils.split(themes, SEPARATOR);
					for (String str : split) {
						obj = themeService.deleteTheme(q.getId(), str);
						if (!ReturnCode.ERROR_000.equals(obj.getCode())) {
							log.error("Error during addTheme, impossible to add question");
						}
					}
					questionService.deleteQuestion(q.getId());
				}
			}
		} else {
			obj.setCode(ReturnCode.ERROR_100);
		}
		return obj;
	}

	@Override
	public ReturnObject getTenLastScoreForQuizz(String pseudo, Integer quizzId) {
		log.info("Get ten Last score for quizz. [pseudo" + pseudo + " quizzId:" + quizzId + "] ");
		ReturnObject obj = userService.getUser(pseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			return statisticService.getTenLastScoreForQuizz(pseudo, quizzId);
		}
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject addScoreForQuizz(String pseudo, Integer quizzId, String quizzName, Integer nbRightAnswers,
			Integer nbQuestions) {
		ReturnObject obj = userService.getUser(pseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			return statisticService.addScoreForQuizz(pseudo, quizzId, quizzName, nbRightAnswers, nbQuestions);
		}
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject addShareQuizz(Integer quizzId, String userSharedPseudo) {
		log.info("Share quizz [quizzId: " + quizzId + " userSharedPseudo: " + userSharedPseudo + "]");
		ReturnObject obj = userService.getUser(userSharedPseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {

			UserBean quizzReceiver = ((User) obj.getObject()).convertToBean();
			obj = quizzService.shareQuizzToUser(quizzId, quizzReceiver);
			if (!ReturnCode.ERROR_000.equals(obj.getCode())) {
				log.error("Impossible to share quizz [quizzId: " + quizzId + " userSharedPseudo: " + userSharedPseudo
						+ "]");
			}
			// Do not need on return statement
			obj.setObject(null);
			return obj;
		}
		log.error("Impossible te share quizz, target user unreacheable [pseudo: " + userSharedPseudo + "]");
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject deleteSharedQuizz(Integer quizzId, String userSharedPseudo) {
		ReturnObject obj = userService.getUser(userSharedPseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			UserBean quizzReceiver = ((User) obj.getObject()).convertToBean();
			return quizzService.deleteSharedQuizz(quizzId, quizzReceiver);
		}
		log.error("Impossible te share quizz, target user unreacheable [pseudo: " + userSharedPseudo + "]");
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@SuppressWarnings("unchecked")
	public ReturnObject getAllFriendsByPseudo(String pseudo) {
		log.info("Get all friends by pseudo. [pseudo" + pseudo + "] ");
		// Object with User
		ReturnObject obj = new ReturnObject();

		obj = userService.getUser(pseudo);
		if (obj.getCode() != ReturnCode.ERROR_000) {
			obj.setCode(ReturnCode.ERROR_100);
			return obj;
		}

		User user = (User) obj.getObject();
		List<User> friendList = (ArrayList<User>) user.getFriends();
		String mail;
		if (CollectionUtils.isNotEmpty(friendList)) {
			for (User friend : friendList) {
				obj = getOwnQuizzesByPseudo(friend.getPseudo());
				mail = (userService.getUserBean(friend.getPseudo())).getMail();
				if (!ReturnCode.ERROR_000.equals(obj.getCode())) {
					obj.setCode(ReturnCode.ERROR_100);
					return obj;
				}
				// Only add public quizz
				List<Quizz> list = new ArrayList<Quizz>();
				for (Quizz q : (ArrayList<Quizz>) obj.getObject()) {
					if (!Visibility.PRIVATE.equals(q.getIsVisible())) {
						list.add(q);
					}
				}
				friend.setQuizz(list);

				friend.setMail(mail);
			}
		} else {
			friendList = null;
		}
		obj.setObject(friendList);
		return obj;
	}

	@Override
	public ReturnObject getOwnQuizzesByPseudo(String pseudo) {
		log.info(" get own Quizzes By Pseudo. [pseudo: " + pseudo + "] ");
		ReturnObject obj = userService.getUser(pseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			User tmp = (User) obj.getObject();
			// Get own quizzs
			List<Quizz> lQuizz = new ArrayList<Quizz>();

			if (CollectionUtils.isNotEmpty(tmp.getQuestions())) {
				Collection<QuestionBean> qBean = new ArrayList<QuestionBean>();
				for (Question q : tmp.getQuestions()) {
					qBean.add(q.convertToBean());
				}
				try {
					lQuizz.addAll((List<Quizz>) quizzService.getAllQuizzesByQuestionBean(qBean).getObject());
				} catch (Exception e) {
					// User have no quizz
				}
			}
			log.info("Get own quizz successful");
			obj.setObject(lQuizz);
			return obj;
		}

		// User not found
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject addFriendbyPseudo(String pseudo, String friendPseudo) {
		log.info(" add a friend. [friendPseudo: " + friendPseudo + " pseudo" + pseudo + "] ");
		ReturnObject object = new ReturnObject();
		UserBean user1 = userService.getUserBean(pseudo);
		object.setObject(user1);
		if (user1 == null) {
			object.setCode(ReturnCode.ERROR_100);
			return object;
		}
		UserBean user2 = userService.getUserBean(friendPseudo);
		object.setObject(user2);
		if (user2 == null) {
			object.setCode(ReturnCode.ERROR_100);
			return object;
		}
		return userService.addFriendbyPseudo(user1, user2);
	}

	@Override
	public ReturnObject deleteFriend(String pseudo, String friendPseudo) {
		log.info(" delete a friend. [friendPseudo: " + friendPseudo + " pseudo" + pseudo + "] ");
		ReturnObject object = new ReturnObject();
		UserBean user1 = userService.getUserBean(pseudo);
		object.setObject(user1);
		if (user1 == null) {
			object.setCode(ReturnCode.ERROR_100);
			return object;
		}
		UserBean user2 = userService.getUserBean(friendPseudo);
		object.setObject(user2);
		if (user2 == null) {
			object.setCode(ReturnCode.ERROR_100);
			return object;
		}
		return userService.deleteFriend(user1, user2);
	}

	@Override
	public ReturnObject searchUserByPartialPseudo(String partialPseudo, String pseudo) {
		log.info(" search User By Partial Pseudo. [partialPseudo" + partialPseudo + "] ");
		return userService.searchUserByPartialPseudo(partialPseudo, pseudo);
	}

	@Override
	public ReturnObject createEvaluation(String evaluatorPseudo, String targetPseudo, Integer quizzId, String quizzName,
			Date deadLine, Integer timer) {
		log.info("Create evaluation for [pseudo: " + targetPseudo + ", quizzId: " + quizzId + "]");

		ReturnObject obj = userService.getUser(evaluatorPseudo);
		if (!evaluatorPseudo.equals(((User) obj.getObject()).getPseudo())) {
			log.error("Evaluator pseudo not found [pseudo: " + evaluatorPseudo + "]");
			obj.setCode(ReturnCode.ERROR_100);
			return obj;
		}

		obj = userService.getUser(targetPseudo);
		if (!targetPseudo.equals(((User) obj.getObject()).getPseudo())) {
			log.error("Target pseudo not found [pseudo: " + targetPseudo + "]");
			obj.setCode(ReturnCode.ERROR_100);
			return obj;
		}
		return evaluationService.createEvaluation(evaluatorPseudo, targetPseudo, quizzId, quizzName, deadLine, timer);
	}

	@Override
	public ReturnObject createEvaluations(String evaluatorPseudo, String targetPseudos, Integer quizzId,
			String quizzName, Date deadLine, Integer timer) {
		log.info("Create multiple evaluation for [pseudos: " + targetPseudos + ", quizzId: " + quizzId + "]");

		ReturnObject obj = userService.getUser(evaluatorPseudo);
		if (!evaluatorPseudo.equals(((User) obj.getObject()).getPseudo())) {
			log.error("Evaluator pseudo not found [pseudo: " + evaluatorPseudo + "]");
			obj.setCode(ReturnCode.ERROR_100);
			return obj;
		}
		String[] pseudos = StringUtils.split(targetPseudos, SEPARATOR);

		for (String targetPseudo : pseudos) {
			obj = userService.getUser(targetPseudo);
			if (!targetPseudo.equals(((User) obj.getObject()).getPseudo())) {
				log.error("Evaluator pseudo not found [pseudo: " + targetPseudo + "]");
				obj.setCode(ReturnCode.ERROR_100);
			} else {
				evaluationService.createEvaluation(evaluatorPseudo, targetPseudo, quizzId, quizzName, deadLine, timer);
			}
		}

		return obj;

	}

	@Override
	public ReturnObject makeDone(String targetPseudo, Integer id) {
		log.info("Make done evaluation [pseudo: " + targetPseudo + ",id: " + id + "]");
		ReturnObject obj = userService.getUser(targetPseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			return evaluationService.makeDone(targetPseudo, id);
		}
		log.error("Evaluator pseudo not found [pseudo: " + targetPseudo + "]");
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject getEvaluationsForPseudo(String targetPseudo) {
		log.info(" get evalution Quizzes for Pseudo. [pseudo: " + targetPseudo + "] ");
		ReturnObject obj = userService.getUser(targetPseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			log.info("Get quizz id with evaluation for [" + targetPseudo + "]");

			obj = evaluationService.getEvaluationsForPseudo(targetPseudo);
			if (ReturnCode.ERROR_000.equals(obj.getCode())) {
				log.error("Error during get evaluation for pseudo [" + targetPseudo + "]");
			}
			return obj;
		}
		// User not found
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject getEvaluationsForEvaluatorPseudo(String pseudo) {
		log.info(" get evaluations Quizzes for Pseudo. [pseudo: " + pseudo + "] ");
		ReturnObject obj = userService.getUser(pseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			log.info("Get quizz id with evaluation for [" + pseudo + "]");
			obj = evaluationService.getEvaluationsForEvaluatorPseudo(pseudo);
			if (!ReturnCode.ERROR_000.equals(obj.getCode())) {
				log.error("Error during get evaluation for evaluator pseudo [" + pseudo + "]");
				obj.setCode(ReturnCode.ERROR_050);
			}
			return obj;
		}
		// User not found
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject getEvaluationsStatisticsForEvaluatorPseudo(String pseudo, Integer quizzId) {
		log.info(" get statistics Quizzes for Pseudo. [pseudo: " + pseudo + "] ");
		ReturnObject obj = userService.getUser(pseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {

			// Get All user which have to do evaluation
			obj = getEvaluationsForEvaluatorPseudo(pseudo);

			List<Evaluation> evaluations = (List<Evaluation>) obj.getObject();

			List<Statistic> statistics = new ArrayList<Statistic>();

			for (Evaluation eval : evaluations) {
				log.info("Get statistics for evaluation [pseudo: " + pseudo + ", quizzid: " + eval.getQuizzId() + "]");
				ReturnObject ro = statisticService.getStatisticByPseudoAndQuizzId(eval.getTargetPseudo(),
						eval.getQuizzId());
				if (ReturnCode.ERROR_000.equals(ro.getCode())) {
					Statistic stat = (Statistic) ro.getObject();
					statistics.add(stat);
				} else {
					log.error(
							"Impossible to get statistic with for [pseudo: " + pseudo + ", quizzId: " + quizzId + "]");
				}
			}

			obj.setObject(statistics);
			return obj;
		}

		// User not found
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}

	@Override
	public ReturnObject getEvaluation(String targetPseudo, Integer quizzId) {
		log.info(" get evalution [pseudo: " + targetPseudo + ", quizzId: " + quizzId + "]");
		ReturnObject obj = userService.getUser(targetPseudo);
		if (StringUtils.isNotBlank(((User) obj.getObject()).getPseudo())) {
			obj = evaluationService.getEvaluations(targetPseudo, quizzId);
			if (ReturnCode.ERROR_000.equals(obj.getCode())) {
				log.error("Error during get evaluation for pseudo [" + targetPseudo + "]");
			}
			return obj;
		}
		// User not found
		obj.setCode(ReturnCode.ERROR_100);
		return obj;
	}
}
