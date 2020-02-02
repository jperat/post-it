package com.jperat.postit.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jperat.postit.dao.PostDao;
import com.jperat.postit.dao.UserDao;
import com.jperat.postit.model.Comment;
import com.jperat.postit.model.Post;
import com.jperat.postit.model.User;
import com.jperat.postit.util.Tools;

@Controller
public class UserController {

	@Autowired
	UserDao userDao;
	
	@Autowired
	PostDao postDap;
	
	@RequestMapping(value="/profile/{id}", method=RequestMethod.GET)
	public String profilePage(@PathVariable("id") long id, ModelMap model, HttpSession session, HttpServletRequest request, Principal principal) {
		User user;
		List<Post> posts;
		User principalUser = userDao.findByEmail(principal.getName());
		model.addAttribute("principalUser", principalUser);
		if (id == principalUser.getId()) {
			user = principalUser;
		} else {
			user = userDao.findById(id);
		}

		posts = postDap.findByUser(user, 0);
		model.addAttribute("user", user);
		model.addAttribute("posts", posts);
		model.addAttribute("comment", new Comment());
		return "User/profile";
	}
	
}
