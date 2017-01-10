package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.beans.QuizzBean;
import com.quizz.database.beans.ResponseBean;
import com.quizz.database.beans.ThemeBean;
import com.quizz.database.beans.UserBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.datas.Visibility;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.Quizz;
import com.quizz.database.modeles.Response;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.Theme;
import com.quizz.database.modeles.User;
import com.quizz.database.repository.QuestionRepository;
import com.quizz.database.repository.QuizzRepository;
import com.quizz.database.repository.UserRepository;
import com.quizz.database.services.QuizzService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuizzServiceImpl implements QuizzService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuizzRepository quizzRepository;

	@Override
	public ReturnObject addQuizz(String name, Visibility visibility, Collection<Question> questions) {
		log.info("Add quizz [name: " + name + "]");
		ReturnObject object = new ReturnObject();
		Quizz quizz = new Quizz(name, visibility, questions);

		// The quizz does not exist
		QuizzBean q = quizz.convertToBean();
		try {
			for (Question item : questions) {
				if (!questionRepository.exists(item.getId())) {
					log.info("Question [id: " + item.getId() + "] doesn't exist");
					object.setCode(ReturnCode.ERROR_150);
					return object;
				}
			}
			// Save method was automatically managed by CrudRepository
			QuizzBean quizzBean = quizzRepository.save(q);
			quizz = new Quizz(quizzBean.getId(), quizzBean.getName(),
					visibility.getVisibility(Integer.parseInt(quizzBean.getIsVisible())), questions);

			object.setCode(ReturnCode.ERROR_000);
			log.info("Quizz successfully added");
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to add Quizz [name: " + name + "], " + ReturnCode.ERROR_500, e);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to add Quizz [name: " + name + "], " + ReturnCode.ERROR_200, e);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to add Quizz [name: " + name + "], " + ReturnCode.ERROR_050, e);
		}
		object.setObject(quizz);
		return object;
	}

	@Override
	public ReturnObject getAllQuizzesByQuestionBean(Collection<QuestionBean> questions) {
		log.info("Get all quizzes by pseudo");
		ReturnObject object = new ReturnObject();

		try {
			List<Quizz> listQuizzes = new ArrayList<Quizz>();

			// Recovers Quizz associated with user questions
			for (QuestionBean qBean : questions) {
				QuestionBean questionBean = questionRepository.findOne(qBean.getId());
				for (QuizzBean quizzBean : new ArrayList<QuizzBean>(questionBean.getQuizzs())) {
					if (!checkIfQuizzInList(listQuizzes, quizzBean.getId())) {
						listQuizzes.add(getQuizzByQuizzBean(quizzBean));
					}
				}
			}
			object.setObject(listQuizzes);
			object.setCode(ReturnCode.ERROR_000);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("An exception has occured when calling getAllQuizzesByQuestionBean, " + ReturnCode.ERROR_050);
		}

		return object;
	}

	/**
	 * Return a Quizz by name
	 * 
	 * @param name
	 *            : name of a quizz
	 * @return {@link Quizz}
	 */
	@Override
	public ReturnObject getQuizzByName(String name) {
		log.info("Get quizz by name [name: " + name + "]");
		ReturnObject object = new ReturnObject();

		try {
			QuizzBean quizzBean = quizzRepository.findByName(name);
			if (quizzBean == null) {
				object.setCode(ReturnCode.ERROR_050);
			} else {
				object.setObject(getQuizzByQuizzBean(quizzBean));
				object.setCode(ReturnCode.ERROR_000);
			}
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("An exception has occured when calling getQuizzByName [name: " + name + "], "
					+ ReturnCode.ERROR_050);
		}

		return object;
	}

	/**
	 * Private method to check if a Quizz is in a list of Quizz (Check by id)
	 * 
	 * @param lQuizzes
	 *            : a list of Quizz
	 * @param idQuizz
	 *            : id of a Quizz
	 * @return boolean : true / false if Quizz in the list or not
	 */
	private boolean checkIfQuizzInList(List<Quizz> lQuizzes, Integer idQuizz) {
		for (Quizz qB : lQuizzes) {
			if (qB.getId() == idQuizz) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Private method to convert QuizzBean to Quizz
	 * 
	 * @param QuizzBean
	 *            bean
	 * @return {@link Quizz}
	 */
	private Quizz getQuizzByQuizzBean(QuizzBean bean) {
		Quizz quizz = new Quizz();
		quizz.setId(bean.getId());
		quizz.setName(bean.getName());
		Visibility vis = null;
		for (Visibility v : Visibility.values()) {
			if (v.getId() == Integer.parseInt(bean.getIsVisible())) {
				vis = v;
			}
		}
		quizz.setIsVisible(vis);

		Collection<Question> questions = new ArrayList<Question>();
		if (bean.getQuestions() != null) {
			for (QuestionBean question : bean.getQuestions()) {
				Question q = new Question();
				q.setId(question.getId());
				q.setPseudo(question.getPseudo());
				q.setLabel(question.getLabel());
				q.setExplanation(question.getExplanation());

				if (question.getThemes() != null) {
					Collection<Theme> themes = new ArrayList<Theme>();
					for (ThemeBean theme : new ArrayList<ThemeBean>(question.getThemes())) {
						Theme t = new Theme();
						t.setId(theme.getId());
						t.setName(theme.getName());
						themes.add(t);
					}
					q.setThemes(themes);
				}

				if (question.getResponses() != null) {
					Collection<Response> responses = new ArrayList<Response>();
					for (ResponseBean response : new ArrayList<ResponseBean>(question.getResponses())) {
						Response r = new Response();
						r.setId(response.getId());
						r.setIsValide(response.getIsValide());
						r.setLabel(response.getLabel());
						responses.add(r);
					}
					q.setResponses(responses);
				}

				questions.add(q);
			}
		}
		quizz.setQuestions(questions);
		Collection<User> sharedUsers = new ArrayList<User>();
		if (CollectionUtils.isNotEmpty(bean.getSharedUser())) {
			for (UserBean sU : bean.getSharedUser()) {
				User user = new User();
				if (sU != null) {
					user.setMail(sU.getMail());
					user.setPseudo(sU.getPseudo());
					user.setActive(sU.getActive());
					Collection<User> friends = new ArrayList<User>();
					for (UserBean userB : sU.getFriends()) {
						User u = new User();
						u.setPseudo(userB.getPseudo());
						friends.add(u);
					}
					user.setFriends(friends);

					Collection<Question> questionsSU = new ArrayList<Question>();
					for (QuestionBean question : sU.getQuestion()) {
						Question q = new Question();
						q.setId(question.getId());
						questions.add(q);
					}
					user.setQuestions(questionsSU);

					if (CollectionUtils.isNotEmpty(sU.getReiceivedQuizz())) {
						Collection<Quizz> quizzs = new ArrayList<Quizz>();
						for (QuizzBean quizzb : sU.getReiceivedQuizz()) {
							Quizz quizzSU = new Quizz();
							quizz.setId(quizzb.getId());
							quizz.setName(quizzb.getName());
							Visibility visSU = null;
							for (Visibility v : Visibility.values()) {
								if (v.getId() == Integer.parseInt(quizzb.getIsVisible())) {
									visSU = v;
								}
							}
							quizz.setIsVisible(visSU);

							Collection<Question> questionsQ = new ArrayList<Question>();
							if (quizzb.getQuestions() != null) {
								for (QuestionBean question : quizzb.getQuestions()) {
									Question q = new Question();
									q.setId(question.getId());
									q.setPseudo(question.getPseudo());
									q.setLabel(question.getLabel());
									q.setExplanation(question.getExplanation());

									if (question.getThemes() != null) {
										Collection<Theme> themes = new ArrayList<Theme>();
										for (ThemeBean theme : new ArrayList<ThemeBean>(question.getThemes())) {
											Theme t = new Theme();
											t.setId(theme.getId());
											t.setName(theme.getName());
											themes.add(t);
										}
										q.setThemes(themes);
									}

									if (question.getResponses() != null) {
										Collection<Response> responses = new ArrayList<Response>();
										for (ResponseBean response : new ArrayList<ResponseBean>(
												question.getResponses())) {
											Response r = new Response();
											r.setId(response.getId());
											r.setIsValide(response.getIsValide());
											r.setLabel(response.getLabel());
											responses.add(r);
										}
										q.setResponses(responses);
									}

									questionsQ.add(q);
								}
							}
							quizz.setQuestions(questionsQ);

							quizzs.add(quizzSU);
						}
						user.setReiceivedQuizz(quizzs);
					}
				}
				sharedUsers.add(user);
			}
			quizz.setSharedUser(sharedUsers);
		}

		return quizz;
	}

	@Override
	public ReturnObject deleteQuizzById(Integer id) {
		log.info("Delete Quiz [id: " + id + "]");
		ReturnObject object = new ReturnObject();
		try {
			quizzRepository.delete(id);
			object.setCode(ReturnCode.ERROR_000);
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_100);
		}
		return object;
	}

	@Override
	public ReturnObject shareQuizzToUser(Integer quizzId, UserBean quizzReceiver) {
		log.info("Share quizz [quizzId: " + quizzId + ", userSharedPseudo: " + quizzReceiver.getPseudo() + "]");
		ReturnObject object = new ReturnObject();

		QuizzBean quizzToShare = new QuizzBean();
		try {

			quizzToShare = quizzRepository.findOne(quizzId);
			if (quizzToShare == null) {
				log.info("Impossible to found Quizz [quizzId:" + quizzId + "]");
				object.setCode(ReturnCode.ERROR_125);
				return object;
			}

			// Check if Quizz was created by target user
			for (QuestionBean q : quizzToShare.getQuestions()) {
				if (quizzReceiver.getPseudo().equals(q.getPseudo())) {
					log.info("Impossible to found Quizz [quizzId:" + quizzId + "]");
					object.setCode(ReturnCode.ERROR_325);
					return object;
				}
			}

			for (UserBean user : quizzToShare.getSharedUser()) {
				if (quizzReceiver.getPseudo().equals(user.getPseudo())) {
					log.info("quizz already shared with this user [quizzId: " + quizzId + ", pseudo: "
							+ quizzReceiver.getPseudo() + "]");
					object.setCode(ReturnCode.ERROR_325);
					return object;
				}
			}

			if (CollectionUtils.isEmpty(quizzToShare.getSharedUser())) {
				quizzToShare.setSharedUser(new ArrayList<UserBean>());
			}

			quizzToShare.getSharedUser().add(quizzReceiver);

			quizzToShare = quizzRepository.save(quizzToShare);
			if (quizzToShare != null) {
				object.setCode(ReturnCode.ERROR_000);
				log.info("Quizz successfully shared");
			} else {
				object.setCode(ReturnCode.ERROR_050);
				log.error("Impossible to Share Quizz " + quizzId + ", " + ReturnCode.ERROR_050);
			}

		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to Share Quizz " + quizzId + ", " + ReturnCode.ERROR_500, e);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to Share Quizz " + quizzId + ", " + ReturnCode.ERROR_200, e);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to Share Quizz " + quizzId + ", " + ReturnCode.ERROR_050, e);
		}
		object.setObject(getQuizzByQuizzBean(quizzToShare));
		return object;
	}

	@Override
	public ReturnObject deleteSharedQuizz(Integer quizzId, UserBean userSharedPseudo) {
		log.info("Delete shared quizz [quizzId: " + quizzId + ", userSharedPseudo: " + userSharedPseudo + "]");
		ReturnObject obj = new ReturnObject();
		try {
			QuizzBean quizzToUnShare = quizzRepository.findOne(quizzId);
			boolean isShared = false;
			for (UserBean user : quizzToUnShare.getSharedUser()) {
				if (user.getPseudo().equals(userSharedPseudo.getPseudo())) {
					isShared = true;
					break;
				}
			}
			if (Boolean.TRUE.equals(isShared)) {
				//First, delete receivedQuizz on User
				Collection<QuizzBean> reiceivedQuizz = userSharedPseudo.getReiceivedQuizz();
				for (QuizzBean quizzBean : reiceivedQuizz) {
					if(quizzToUnShare.getId() == quizzBean.getId()){
						reiceivedQuizz.remove(quizzBean);
						break;
					}
				}
				userSharedPseudo.setReiceivedQuizz(reiceivedQuizz);
								
				//Second, delete sharedQuizz on Quizz
				Collection<UserBean> sharedUsers = quizzToUnShare.getSharedUser();
				for (UserBean userBean : sharedUsers) {
					if(userSharedPseudo.getPseudo().equals(userBean.getPseudo())){
						sharedUsers.remove(userBean);
						break;
					}
				}
				quizzToUnShare.setSharedUser(sharedUsers);
				
				
				quizzRepository.save(quizzToUnShare);
				
				obj.setCode(ReturnCode.ERROR_000);
			} else {
				log.info("Quizz was not shared with this user");
				obj.setCode(ReturnCode.ERROR_450);
				return obj;
			}

		} catch (IllegalArgumentException e) {
			obj.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to delete shared quizz [quizzId: " + quizzId + ", userSharedPseudo: "
					+ userSharedPseudo + "]");
		} catch (Exception e) {
			obj.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to delete shared quizz [quizzId: " + quizzId + ", userSharedPseudo: "
					+ userSharedPseudo + "]");

		}
		return obj;
	}

}
