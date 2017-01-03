package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.QuestionBean;
import com.quizz.database.beans.QuizzBean;
import com.quizz.database.beans.UserBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.Quizz;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;
import com.quizz.database.services.FriendsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FriendsServiceImpl implements FriendsService {
	
	/**
	 * Return a list of Quizz 
	 * 
	 * @param pseudo : pseudo of an user
	 * @return {@link List<Quizz>}
	 */
	@Override
	public ReturnObject getAllFriendsByPseudo(UserBean userBean) {
		log.info("Get all friends by pseudo");
		ReturnObject object = new ReturnObject();
		
		try {
			List<User> listFriends = new ArrayList<User>();
			
			// Recovers Users associated with user questions
			for (UserBean userB : new ArrayList<UserBean>(userBean.getFriends())) {
				User user = UserServiceImpl.getUserByUserBean(userB);
				listFriends.add(user);
			}
						
			object.setObject(listFriends);
			object.setCode(ReturnCode.ERROR_000);
		}
		catch(Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("An exception has occured when calling getAllFriendsByPseudo [pseudo: " + userBean.getPseudo() + "], " + ReturnCode.ERROR_050);
		}
		
		return object;
	}

}
