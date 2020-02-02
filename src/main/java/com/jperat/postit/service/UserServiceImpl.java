package com.jperat.postit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jperat.postit.dao.UserDao;
import com.jperat.postit.exception.EmailExistsException;
import com.jperat.postit.exception.NotFoundEmailException;
import com.jperat.postit.model.User;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	private static final String RESET_PASSWORD_EMAIL_SUBJECT = "Reset email";
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EmailService emailService;

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	public User registerNewUserAccount(User user) throws EmailExistsException {
		
		if (emailExist(user.getEmail())) {
			throw new EmailExistsException(
		              "There is an account with that email adress: "
		              +  user.getEmail());
		}
		
		return userDao.createNewUser(user);
	}
	
	private boolean emailExist(String email) {
		User user = userDao.findByEmail(email);
		if (user == null) {
			return false;
		}
		return true;
	}
	
	public User forgotPasswordReset(String email) throws NotFoundEmailException{
		User user = userDao.findByEmail(email);
		if (user == null) {
			throw new NotFoundEmailException("No account with email : "+email);
		}
		userDao.createNewToken(user);
		return user;
	}

	public boolean addFollow(User user, Long id) {
		User follow = userDao.findById(id);
		if (user != null)
			return addFollow(user, follow);
		return false;
	}

	public boolean addFollow(User user, User follow) {
		if (!user.getFollows().contains(follow.getId()) && !follow.getFollowers().contains(user.getId()) ) {
			user.getFollows().add(follow.getId());
			follow.getFollowers().add(user.getId());
			userDao.updateOrSave(user);
			userDao.updateOrSave(follow);
			return true;
		}
		return false;
	}

	public boolean deleteFollow(User user, Long id) {
		User follow = userDao.findById(id);
		if (follow != null)
			return deleteFollow(user, follow);
		return false;
	}

	public boolean deleteFollow(User user, User follow) {
		if (user.getFollows().remove(follow.getId()) && follow.getFollowers().remove(user.getId()) ) {
			userDao.updateOrSave(user);
			userDao.updateOrSave(follow);
			return true;
		}
		return false;
	}

}
