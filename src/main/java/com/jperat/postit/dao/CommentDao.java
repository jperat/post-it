package com.jperat.postit.dao;

import java.util.Date;
import java.util.List;

import com.jperat.postit.model.Comment;
import com.jperat.postit.model.Post;

public interface CommentDao {

	Comment findById(Long id);
	
	void saveOrUpdate(Comment comment);
	
	void persist(Comment comment);
	
	void remove(Comment comment);
	
	List<Comment> findByPost(Post post, int first);
	
	public List<Comment> findByPost(Post post, Date date);
	
	List<Comment> findByPostId(Long id);
}
