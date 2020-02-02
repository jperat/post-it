package com.jperat.postit.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jperat.postit.model.Comment;
import com.jperat.postit.model.Post;

@Repository("commentDao")
@Transactional
public class CommentDaoImpl implements CommentDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
	    return sessionFactory.getCurrentSession();
	}

	public Comment findById(Long id) {
		return getSession().createQuery("FROM Comment c WHERE c.id = :id", Comment.class).setParameter("id", id).getSingleResult();
	}

	public void saveOrUpdate(Comment comment) {
		getSession().saveOrUpdate(comment);		
	}

	public void persist(Comment comment) {
		getSession().persist(comment);
	}
	
	public void remove(Comment comment) {
		getSession().remove(comment);
	}

	public List<Comment> findByPost(Post post, int first) {
		return getSession().createQuery("FROM Comment c WHERE c.post = :post ORDER BY c.date DESC", Comment.class)
		.setParameter("post", post)
		.setFirstResult(first)
		.setMaxResults(3)
		.getResultList();
	}
	
	public List<Comment> findByPost(Post post, Date date) {
		return getSession().createQuery("FROM Comment c WHERE c.post = :post AND c.date < :date ORDER BY c.date DESC", Comment.class)
		.setParameter("post", post)
		.setParameter("date", date)
		.setMaxResults(3)
		.getResultList();
	}

	public List<Comment> findByPostId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
