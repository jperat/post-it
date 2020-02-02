package com.jperat.postit.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.fasterxml.jackson.annotation.JsonView;
import com.jperat.postit.dao.CommentDao;
import com.jperat.postit.dao.PostDao;
import com.jperat.postit.dao.UserDao;
import com.jperat.postit.jsonview.RenderHtml;
import com.jperat.postit.jsonview.Views;
import com.jperat.postit.model.Comment;
import com.jperat.postit.model.Post;
import com.jperat.postit.model.User;
import com.jperat.postit.service.PostService;
import com.jperat.postit.service.UserService;

@RestController
public class AjaxPostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	ViewResolver viewResolver;
	
	@Autowired
	UserService userService;

	@JsonView(Views.Public.class)
	@RequestMapping(value="/newpost", method = RequestMethod.POST)
	public RenderHtml newPost(@Valid @ModelAttribute Post post, @RequestParam(value="image", required=false) MultipartFile image, BindingResult result, ModelMap model, HttpServletRequest request, Principal principal) {
		RenderHtml renderHtml = new RenderHtml();
		String view;
		
		List<ObjectError> errors = new ArrayList<ObjectError>();
		errors.addAll(result.getAllErrors());
		if (post.getMessage().isEmpty() && image.isEmpty()) {
			errors.add(new ObjectError("message", "You need to write a message"));
		}
		
		if (errors.isEmpty()) {
			post.setUser(userDao.findByEmail(principal.getName()));

			postService.newPost(post, image);
			
			post = new Post();
			model.addAttribute("success", "Your post has been saved!");
			model.addAttribute("post", post);
			renderHtml.setCode("200");
			renderHtml.setMsg("Your post has been saved!");
		} else {
			model.addAttribute("errors", errors);
			model.addAttribute("post", post);
			renderHtml.setCode("400");
		}
		view = "Post/post_form";

		try {
			View resolvedView = viewResolver.resolveViewName(view, Locale.FRANCE);
			MockHttpServletResponse mockResp = new MockHttpServletResponse();
			resolvedView.render(model, request, mockResp);
			renderHtml.setRender(mockResp.getContentAsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return renderHtml;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value="/post/remove/{id}", method = RequestMethod.DELETE)
	public RenderHtml removePost(@PathVariable("id") Long id,ModelMap model, HttpSession session, HttpServletRequest request, Principal principal) {
		RenderHtml renderHtml = new RenderHtml();
		User user = userService.findByEmail(principal.getName());
		if (postService.removePost(user, id))
			renderHtml.setCode("200");
		else
			renderHtml.setCode("400");
		return renderHtml;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value="/like", method = RequestMethod.POST)
	public RenderHtml like(ModelMap model, HttpSession session, HttpServletRequest request, Principal principal) {
		RenderHtml renderHtml = new RenderHtml();
		User user = userService.findByEmail(principal.getName());
		Long id;
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e) {
			id = null;
		}
		if (id != null && postService.addLike(user, id))
			renderHtml.setCode("200");
		else
			renderHtml.setCode("400");
		
		return renderHtml;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value="/unlike", method = RequestMethod.POST)
	public RenderHtml unlike(ModelMap model, HttpSession session, HttpServletRequest request, Principal principal) {
		RenderHtml renderHtml = new RenderHtml();
		User user = userService.findByEmail(principal.getName());
		Long id;
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e) {
			id = null;
		}
		if (id != null && postService.removeLike(user, id))
			renderHtml.setCode("200");
		else
			renderHtml.setCode("400");
		
		return renderHtml;
	}
	
	
	
	public String newImagePost() {
		
		return "Post/post_form";
		
	}
}
