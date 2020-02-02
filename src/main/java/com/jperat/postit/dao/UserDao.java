package com.jperat.postit.dao;

import java.util.List;

import com.jperat.postit.exception.EmailExistsException;
import com.jperat.postit.model.User;

public interface UserDao {

	User findByUserName (String username);
	
	User findByEmail (String email);
	
	User findById(Long id);
	
	User findByTokenAndEmail(String token, String email);
	
	void persist(User user);
	
	void updateOrSave(User user);
	
	User createNewUser (User user) throws EmailExistsException;
	
	User createNewToken (User user);
	
	User updatePassword(User user, String password);
	
	List<User> search(String q, int first);
}
