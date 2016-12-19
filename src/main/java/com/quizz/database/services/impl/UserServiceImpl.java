package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.beans.UserBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.Question;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;
import com.quizz.database.repository.UserRepository;
import com.quizz.database.services.UserService;

import lombok.extern.slf4j.Slf4j;

/*
 * Warning: Never return password !
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Return pseudo, mail, friend (pseudo), question (id)
	 * 
	 * Warning: password was not test
	 * 
	 * @return {@link User}
	 */
	@Override
	public ReturnObject getUser(String pseudo) {
		ReturnObject object = new ReturnObject();

		User result = new User();
		try {
			UserBean tmp = userRepository.findOne(pseudo);
			result = getUserByUserBean(tmp);
			
			if (result != null) {
				object.setCode(ReturnCode.ERROR_000);
				log.info("Get User [pseudo: " + pseudo + "]");
			} else {
				object.setCode(ReturnCode.ERROR_100);
				log.error("User not found [pseudo: " + pseudo + "], " + ReturnCode.ERROR_100);
			}
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_100);
			log.error("User not found [pseudo: " + pseudo + "], " + ReturnCode.ERROR_100);
		}
		object.setObject(result);

		return object;
	}

	/**
	 * Return pseudo and mail
	 * 
	 * @return {@link User}
	 */
	@Override
	public ReturnObject checkUserCredentials(String pseudo, String password) {
		ReturnObject object = new ReturnObject();
		User result = new User();
		try {
			UserBean tmp = userRepository.findByPseudoAndPassword(pseudo, password);
			result = getUserByUserBean(tmp);
			if(tmp == null || result == null){
				object.setCode(ReturnCode.ERROR_100);
			} else {
				if(tmp.getActive()) {
					object.setCode(ReturnCode.ERROR_000);
					log.info("Credentials are great [pseudo: " + pseudo + "]");
				} else {
					object.setCode(ReturnCode.ERROR_650);
					log.info("User is not active [pseudo: " + pseudo + "]");
				}
			}
			log.info("Check if User exist [pseudo: " + pseudo + ", password: *****]");
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_100);
			log.error("User not found [pseudo: " + pseudo + "], password: *****" + ReturnCode.ERROR_100);
		}
		object.setObject(result);
		return object;
	}

	@Override
	public ReturnObject getAllUsers() {
		log.info("Get all User");
		ReturnObject object = new ReturnObject();
		List<User> users = new ArrayList<User>();
		try {
			List<UserBean> findAll = (List<UserBean>) userRepository.findAll();
			for (UserBean userBean : findAll) {
				User user = new User();

				user.setMail(userBean.getMail());
				user.setPseudo(userBean.getPseudo());
				user.setActive(userBean.getActive());

				Collection<User> friends = new ArrayList<User>();
				for (UserBean userB : userBean.getFriends()) {
					User u = new User();
					u.setPseudo(userB.getPseudo());
					friends.add(u);
				}
				user.setFriends(friends);

				Collection<Question> questions = new ArrayList<Question>();
				for (QuestionBean question : userBean.getQuestion()) {
					Question q = new Question();
					q.setId(question.getId());
					questions.add(q);
				}
				user.setQuestions(questions);

				users.add(user);
				object.setCode(ReturnCode.ERROR_000);
			}
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to get all User " + ReturnCode.ERROR_500);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to get all User " + ReturnCode.ERROR_200);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to get all User " + ReturnCode.ERROR_050);
		}
		object.setObject(users);

		return object;
	}

	@Override
	public ReturnObject addUser(String pseudo, String mail, String password) {
		log.info("Add user [pseudo: " + pseudo + ", mail: " + mail + "]");

		ReturnObject object = new ReturnObject();
		User user = new User();
		//Create empty user in case of existing pseudo or mail
		object.setObject(user);
		// Test if pseudo or email was already used
		if (userRepository.exists(pseudo)) {
			log.info("Add user [pseudo: " + pseudo + "] already exist");
			object.setCode(ReturnCode.ERROR_300);
			return object;
		} else if (userRepository.findByMail(mail) != null) {
			log.info("Add user [mail: " + mail + "] already exist");
			object.setCode(ReturnCode.ERROR_350);
			return object;
		}
		// The user does not exist and this mail was not used
		UserBean u = new UserBean(pseudo, password, mail, false);
		try {
			// Save method was automatically managed by CrudRepository
			UserBean userBean = userRepository.save(u);
			user = new User(userBean.getPseudo(), null, userBean.getMail(), false, null, null);
			object.setCode(ReturnCode.ERROR_000);
			log.info("User successfully added");
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to add User [pseudo: " + pseudo + ", mail: " + mail + "], " + ReturnCode.ERROR_500);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to add User [pseudo: " + pseudo + ", mail: " + mail + "], " + ReturnCode.ERROR_200);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to add User [pseudo: " + pseudo + ", mail: " + mail + "], " + ReturnCode.ERROR_050);
		}

		object.setObject(user);
		return object;
	}

	@Override
	public ReturnObject editUser(String pseudo, String mail, String password, Boolean active, Collection<User> friends,
			Collection<Question> questions) {
		log.info("Edit user [pseudo: " + pseudo + ", mail: " + mail + "]");
		ReturnObject object = new ReturnObject();
		User user = null;

		UserBean u = userRepository.findOne(pseudo);
		if (StringUtils.isNotBlank(mail)) {
			u.setMail(mail);
		}

		if (StringUtils.isNotBlank(password)) {
			u.setPassword(password);
		}
		
		u.setActive(active);

		if (CollectionUtils.isNotEmpty(friends)) {
			Collection<UserBean> friendsBean = new ArrayList<UserBean>();
			for (User friend : friends) {
				friendsBean.add(friend.convertToBean());
			}
			u.setFriends(friendsBean);
		}

		if (CollectionUtils.isNotEmpty(questions)) {
			Collection<QuestionBean> questionsBean = new ArrayList<QuestionBean>();
			for (Question question : questions) {
				questionsBean.add(question.convertToBean());
			}
			u.setQuestion(questionsBean);
		}

		try {
			UserBean userBean = userRepository.save(u);

			user = getUserByUserBean(userBean);

			object.setCode(ReturnCode.ERROR_000);
			log.info("User successfully edit");
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to edit User [pseudo: " + pseudo + "], " + ReturnCode.ERROR_500);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to edit User [pseudo: " + pseudo + "], " + ReturnCode.ERROR_200);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to edit User [pseudo: " + pseudo + "], " + ReturnCode.ERROR_050);
		}
		object.setObject(user);

		return object;
	}

	@Override
	public ReturnObject deleteUser(String pseudo) {
		log.info("Delete User [pseudo: " + pseudo + "]");
		ReturnObject object = new ReturnObject();
		try {
			userRepository.delete(pseudo);
			object.setCode(ReturnCode.ERROR_000);
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_100);
		}
		return object;
	}

	@Override
	public ReturnObject getUserByMail(String mail) {
		log.info("Get User [mail: " + mail + "]");
		ReturnObject object = new ReturnObject();
		User user = null;
		try {
			UserBean findByMail = userRepository.findByMail(mail);
			user = getUserByUserBean(findByMail);
			if(StringUtils.isNotBlank(user.getMail())){
				object.setCode(ReturnCode.ERROR_000);
			}else{				
				log.error("User not found [mail: " + mail + "], " + ReturnCode.ERROR_100);
				object.setCode(ReturnCode.ERROR_100);
			}
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to get User [mail: " + mail + "], " + ReturnCode.ERROR_500);
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("Impossible to get User [mail: " + mail + "], " + ReturnCode.ERROR_200);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to get User [mail: " + mail + "], " + ReturnCode.ERROR_050);
		}
		object.setObject(user);
		return object;
	}

	@Override
	public ReturnObject changePassword(String password, String mail) {
		log.info("Edit password User [mail: " + mail + "]");
		ReturnObject object = new ReturnObject();
		User user = new User();
		try {
			object = getUserByMail(mail);
			user = (User) object.getObject();
			if (StringUtils.isNotBlank(password)) {
				object = editUser(user.getPseudo(), user.getMail(), password, user.getActive(), user.getFriends(), user.getQuestions());
			}
			object.setCode(ReturnCode.ERROR_000);
			log.info("Password changed for User [mail: " + mail + "]");
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_350);
			log.error("User can not be modify [mail: " + mail + "]" + ReturnCode.ERROR_100);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_100);
			log.error("User not found [mail: " + mail + "] " + ReturnCode.ERROR_100);
		}
		object.setObject(user);
		return object;
	}

	/**
	 * Convert UserBean to User <br />
	 *  <b><i>Warning</i></b>: Password is not set
	 * 
	 * @param bean {@link UserBean}
	 * @return {@link User}
	 */
	private User getUserByUserBean(UserBean bean) {
		User user = new User();
		if(bean != null){	
			user.setMail(bean.getMail());
			user.setPseudo(bean.getPseudo());
			user.setActive(bean.getActive());
			Collection<User> friends = new ArrayList<User>();
			for (UserBean userB : bean.getFriends()) {
				User u = new User();
				u.setPseudo(userB.getPseudo());
				friends.add(u);
			}
			user.setFriends(friends);
			
			Collection<Question> questions = new ArrayList<Question>();
			for (QuestionBean question : bean.getQuestion()) {
				Question q = new Question();
				q.setId(question.getId());
				questions.add(q);
			}
			user.setQuestions(questions);
		}
		return user;
	}
	
	@Override
	public ReturnObject activeUser(String mail) {
		log.info("Active user [mail: " + mail + "]");
		ReturnObject object = new ReturnObject();
		User user = new User();
		try {
			object = getUserByMail(mail);
			user = (User) object.getObject();
			if (StringUtils.isNotBlank(user.getMail())) {
				object = editUser(user.getPseudo(), user.getMail(), user.getPassword(), true, user.getFriends(), user.getQuestions());
				log.info("User is now active [pseudo: " + user.getPseudo() + "]");
			}
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_100);
			log.error("User not found [pseudo: " + user.getPseudo() + "]" + ReturnCode.ERROR_100);
		}
		object.setObject(null);
		return object;
	}
}
