package com.jperat.postit.service;

import org.springframework.stereotype.Service;

import com.jperat.postit.exception.EmailExistsException;
import com.jperat.postit.exception.NotFoundEmailException;
import com.jperat.postit.model.User;

@Service
public interface UserService {

	User findByEmail (String email);

	User registerNewUserAccount(User user) throws EmailExistsException;
	
	User forgotPasswordReset(String email) throws NotFoundEmailException;
	
	boolean addFollow(User user, Long id);
	
	boolean addFollow(User user, User follow);
	
	boolean deleteFollow(User user, Long id);
	
	boolean deleteFollow(User user, User follow);
}
