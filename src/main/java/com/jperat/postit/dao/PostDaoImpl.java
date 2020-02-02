package com.jperat.postit.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jperat.postit.model.Post;
import com.jperat.postit.model.User;

@Repository("postDao")
@Transactional
public class PostDaoImpl implements PostDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
	    return sessionFactory.getCurrentSession();
	}

	public Post findById(Long id) {
		return getSession().createQuery("FROM Post p WHERE p.id = :id", Post.class).setParameter("id", id).getSingleResult();
	}

	public void saveOrUpdate(Post post) {
		getSession().saveOrUpdate(post);		
	}
	
	public void persist(Post post) {
		getSession().persist(post);
	}
	
	public void remove(Post post) {
		getSession().remove(post);
	}

	public List<Post> findByUser(User user, int first) {
		return getSession().createQuery("FROM Post p WHERE p.user = :user ORDER BY p.date DESC", Post.class)
				.setParameter("user", user)
				.setFirstResult(first)
				.setMaxResults(25)
				.getResultList();
	}

	public List<Post> findByUsersId(List<Long> ids, int first) {
		return getSession()
				.createQuery("From Post p WHERE p.user.id IN (:ids) ORDER BY p.date DESC", Post.class)
				.setParameterList("ids", ids)
				.setFirstResult(first)
				.setMaxResults(25)
				.getResultList();
	}
}
