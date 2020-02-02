package com.jperat.postit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.jperat.postit.dao.PostDao;
import com.jperat.postit.model.Comment;
import com.jperat.postit.model.Post;

@Controller
public class CommentController {
	
	@Autowired
	PostDao postDao;

	public String newCommentForm(Long post_id) {
		ModelMap model = new ModelMap();
		Comment comment = new Comment();
		Post post = postDao.findById(post_id);
		comment.setPost(post);
		
		model.addAttribute("comment", comment);
		
		return "Comment/comment_form";
	}
}
