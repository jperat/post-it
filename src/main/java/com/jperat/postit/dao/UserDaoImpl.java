package com.jperat.postit.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jperat.postit.model.User;
import com.jperat.postit.model.UserRole;

@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	PasswordEncoder passwordEncoder;
		
	private Session getSession() {
	    return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public User findByUserName(String username) {	
		return getSession().createQuery("FROM User u WHERE u.username=:username", User.class).setParameter("username", username).uniqueResult();
	}
	
	@Transactional(readOnly=true)
	public User findById(Long id) {
		return getSession()
				.createQuery("FROM User u WHERE u.id = :id", User.class)
				.setParameter("id", id)
				.getSingleResult();
	}

	public void persist(User user) {		
		sessionFactory.getCurrentSession().persist(user);
	}
	
	public void updateOrSave(User user) {
		getSession().saveOrUpdate(user);
	}

	@Transactional(readOnly=true)
	public User findByEmail(String email) {		
		User user = getSession().createQuery("FROM User u WHERE u.email=:email", User.class).setParameter("email", email).uniqueResult();
		if (user != null)
			Hibernate.initialize(user.getUserRole());
		return user;
	}


	public User createNewUser(User user) {
		user.setEnabled(true);
		UserRole userRole = new UserRole();
		userRole.setRole("ROLE_USER");
		userRole.setUser(user);
		user.getUserRole().add(userRole);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		getSession().save(user);
		return user;
	}

	public User findByTokenAndEmail(String token, String email) {
		return getSession()
				.createQuery("FROM User u WHERE u.token=:token and u.email = :email and u.tokenExpiryDate >= current_date ", User.class)
				.setParameter("token", token)
				.setParameter("email", email)
				.uniqueResult();
	}

	@Transactional
	public User createNewToken(User user) {
		user.setToken(UUID.randomUUID().toString());
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 24); // adds one hour
	    user.setTokenExpiryDate(cal.getTime());
	    getSession().saveOrUpdate(user);
		return user;
	}
	
	public List<User> search(String q, int first) {
		return getSession().createQuery("FROM User u WHERE u.username LIKE :q OR u.firstname LIKE :q OR u.lastname LIKE :q", User.class).setParameter("q", q+"%").setFirstResult(first).setMaxResults(25).getResultList();
	}
	
	public User updatePassword(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		user.setToken("");
		user.setTokenExpiryDate(null);
		getSession().saveOrUpdate(user);
		return user;
	}
}
