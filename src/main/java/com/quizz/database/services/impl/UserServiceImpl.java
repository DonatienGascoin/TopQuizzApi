package com.quizz.database.services.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.User;
import com.quizz.database.repository.UserRepository;
import com.quizz.database.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUser(String pseudo) {
		return userRepository.findOne(pseudo);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User addUser(String pseudo, String mail, String password) {
		// Test if pseudo or email was already used
		if (userRepository.exists(pseudo)) {
			return null;
		} else if (userRepository.findByMail(mail) != null) {
			return null;
		}
		// The user does not exist and this mail wasn't used
		User u = new User(pseudo, password, mail);

		// Save method was automatically managed by CrudRepository
		return userRepository.save(u);
	}

	@Override
	public User editUser(String pseudo, String mail, String password) {
		User u = userRepository.findOne(pseudo);
		if(StringUtils.isNotBlank(mail)){
			u.setMail(mail);
		}
		if(StringUtils.isNotBlank(password)){
			u.setPassword(password);
		}

		return userRepository.save(u);
	}

	@Override
	public boolean deleteUser(String pseudo) {
		try {
			userRepository.delete(pseudo);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public User getUserByMail(String mail) {
		return userRepository.findByMail(mail);
	}
	
	@Override
	public User changePassword(String password, String email) {
		User u = userRepository.findByMail(email);
		if(StringUtils.isNotBlank(password)){
			u.setPassword(password);
		}
		return userRepository.save(u);
	}
	
	

}
