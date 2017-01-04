package com.quizz.database.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.UserBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.modeles.User;
import com.quizz.database.repository.UserRepository;
import com.quizz.database.services.FriendsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FriendsServiceImpl implements FriendsService {
	
	@Autowired
	private UserRepository userRepository;
	
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

	@Override
	public ReturnObject addFriendbyPseudo(String pseudo, String friendPseudo) {
		log.info("Add friend [pseudo: " + pseudo + "]");
		ReturnObject object = new ReturnObject();
		
		UserBean user1 = userRepository.findOne(pseudo);
		UserBean user2 = userRepository.findOne(friendPseudo);
		if (user1 == null || user2==null ){
			object.setCode(ReturnCode.ERROR_100);
		}else if(user1.getFriends().contains(user2) || user2.getFriends().contains(user1)){
				//Test if the pseudoAmi is already a friend
				object.setCode(ReturnCode.ERROR_300);
				log.error("This person is already your friend. [friendPseudo: " + friendPseudo + " pseudo" + pseudo + "], " + ReturnCode.ERROR_300);
			}else{//The person was not a friend yet 
				try {
					//The friend was add here
					user1.getFriends().add(user2);					
					userRepository.save(user1);
					user2.getFriends().add(user1);					
					userRepository.save(user2);	
					object.setCode(ReturnCode.ERROR_000);
					log.info("Friend successfully added");
				} catch (IllegalArgumentException e) {
					object.setCode(ReturnCode.ERROR_500);
					log.error("Impossible to add Friend [friendPseudo: " + friendPseudo + " pseudo" + pseudo + "], " + ReturnCode.ERROR_500);
				} catch (RuntimeException e) {
					object.setCode(ReturnCode.ERROR_200);
					log.error("Impossible to add Friend [friendPseudo: " + friendPseudo + " pseudo" + pseudo + "], " + ReturnCode.ERROR_200);
				} catch (Exception e) {
					object.setCode(ReturnCode.ERROR_050);
					log.error("Impossible to add Friend [friendPseudo: " + friendPseudo + " pseudo" + pseudo + "], " + ReturnCode.ERROR_050);
				}
		}
		object.setObject(null);
		return object;
	}

	@Override
	public ReturnObject deleteFriend(String pseudo, String friendPseudo) {
		log.info("Delete Friend [friendPseudo: " + friendPseudo + " pseudo" + pseudo + "]");
		ReturnObject object = new ReturnObject();
		try {
			UserBean user1 = userRepository.findOne(pseudo);
			UserBean user2 = userRepository.findOne(friendPseudo);
			if (user1 == null || user2==null ){
				object.setCode(ReturnCode.ERROR_100);
			}else if(user1.getFriends().contains(user2) || user2.getFriends().contains(user1)){
				//the pseudoAmi is already a friend
				user1.getFriends().remove(user2);				
				userRepository.save(user1);
				user2.getFriends().remove(user1);				
				userRepository.save(user2);
				object.setCode(ReturnCode.ERROR_000);
			}else{
				object.setCode(ReturnCode.ERROR_300);
				log.error("This person is already not you friend. [friendPseudo: " + friendPseudo + " pseudo" + pseudo + "], " + ReturnCode.ERROR_100);
			}
		} catch (IllegalArgumentException e) {
			object.setCode(ReturnCode.ERROR_100);
			log.error("Impossible to delete this friend. [friendPseudo: " + friendPseudo + " pseudo" + pseudo + "], " + ReturnCode.ERROR_100);
		}
		object.setObject(null);
		return object;
	}
}
