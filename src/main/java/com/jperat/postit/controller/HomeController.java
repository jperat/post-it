package com.jperat.postit.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jperat.postit.model.Comment;
import com.jperat.postit.model.Post;
import com.jperat.postit.model.User;
import com.jperat.postit.service.PostService;
import com.jperat.postit.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;

	@RequestMapping(path = { "/", "/home" }, method = RequestMethod.GET)
	public String index(ModelMap model, HttpSession session, HttpServletRequest request, Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (request.isUserInRole("ROLE_USER")) {
			User user = userService.findByEmail(principal.getName());
			List<Post> posts = postService.findPostByFollowUser(user, 0);
			model.addAttribute("posts", posts);
			model.addAttribute("post", new Post());
			model.addAttribute("comment", new Comment());
			model.addAttribute("principalUser", user);
			return "Home/index";
		}
		model.addAttribute("user", new User());
		return "Login/homepage_login";
	}
}
