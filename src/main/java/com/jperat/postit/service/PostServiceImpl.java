package com.jperat.postit.service;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jperat.postit.dao.PostDao;
import com.jperat.postit.dao.UserDao;
import com.jperat.postit.model.Post;
import com.jperat.postit.model.User;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	PostDao postDao;

	public void newPost(Post post, MultipartFile file) {
		if (file != null && !file.isEmpty()) {
			post.setPicture(storageService.store(file));
		}
		post.setDate(new Date());
		postDao.persist(post);				
	}
	
	public boolean removePost(User user, Long id) {
		Post post = this.postDao.findById(id);
		if (post != null) {
			if (post.getUser().getId() == user.getId()) {
				if (post.getPicture() != null && !storageService.deleteUploadFile(post.getPicture().getName())) {
					return false;
				}
				this.postDao.remove(post);
				return true;
			}
		}
		return false;
	}

	public List<Post> findPostByFollowUser(User user, int first) {
		if (!user.getFollows().isEmpty())
			return postDao.findByUsersId(user.getFollows(), first);
		return new ArrayList<Post>();
	}


	public boolean addLike(User user, Long id) {
		Post post = postDao.findById(id);
		if (post != null)
			return addLike(user, post);
		return false;
	}


	public boolean addLike(User user, Post post) {
		if (!post.getLikes().contains(user.getId())) {
			post.getLikes().add(user.getId());
			postDao.saveOrUpdate(post);
			return true;
		}
		return false;
	}


	public boolean removeLike(User user, Long id) {
		Post post = postDao.findById(id);
		if (post != null)
			return removeLike(user, post);
		return false;
	}


	public boolean removeLike(User user, Post post) {
		if (post.getLikes().contains(user.getId())) {
			post.getLikes().remove(user.getId());
			postDao.saveOrUpdate(post);
			return true;
		}
		return false;
	}

}
