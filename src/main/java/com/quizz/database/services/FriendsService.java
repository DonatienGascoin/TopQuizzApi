package com.quizz.database.services;

import com.quizz.database.beans.UserBean;
import com.quizz.database.modeles.ReturnObject;

public interface FriendsService {

	public ReturnObject getAllFriendsByPseudo (UserBean userBean);
	
	public ReturnObject addFriendbyPseudo (String pseudo, String friendPseudo);
	
	public ReturnObject deleteFriend(String pseudo, String friendPseudo);
	
}
