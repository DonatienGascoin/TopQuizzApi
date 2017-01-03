package com.quizz.database.services;

import com.quizz.database.beans.UserBean;
import com.quizz.database.modeles.ReturnObject;

public interface FriendsService {

	public ReturnObject getAllFriendsByPseudo (UserBean userBean);
	
}
