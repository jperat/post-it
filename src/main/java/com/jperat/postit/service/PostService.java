package com.jperat.postit.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jperat.postit.model.Post;
import com.jperat.postit.model.User;

public interface PostService {

	void newPost(Post post, MultipartFile file);
	
	boolean removePost(User user, Long id);
	
	List<Post> findPostByFollowUser(User user, int first);
	
	boolean addLike(User user, Long id);
	
	boolean addLike(User user, Post post);
	
	boolean removeLike(User user, Long id);
	
	boolean removeLike(User user, Post post);
}
