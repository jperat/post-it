package com.jperat.postit.dao;

import java.util.List;

import com.jperat.postit.model.Post;
import com.jperat.postit.model.User;

public interface PostDao {
	
	Post findById(Long id);
	
	void saveOrUpdate(Post post);
	
	void persist(Post post);
	
	void remove(Post post);
	
	List<Post> findByUser(User user, int first);
	
	List<Post> findByUsersId(List<Long> ids, int first);
}
