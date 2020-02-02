package com.jperat.postit.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
public class AjaxCommentController {
	
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
	@RequestMapping(value="/newcomment", method = RequestMethod.POST)
	public RenderHtml newComment(@ModelAttribute @Valid Comment comment, BindingResult result, ModelMap model, HttpServletRequest request, Principal principal) {
		RenderHtml renderHtml = new RenderHtml();
		String view;
		List<ObjectError> errors = new ArrayList<ObjectError>();
		errors.addAll(result.getAllErrors());
		
		User user = userService.findByEmail(principal.getName());
		Long id;
		try {
			id = Long.parseLong(request.getParameter("post_id"));
			Post post = postDao.findById(id);
			model.addAttribute("post", post);
			if (post != null) {
				comment.setPost(post);
				comment.setUser(user);
				comment.setDate(new java.util.Date());
				if (errors.isEmpty()) {
					commentDao.persist(comment);
					model.addAttribute("success", "Your comment has been saved!");
					renderHtml.setCode("200");
					view = "Comment/comment";
				} else {
					renderHtml.setCode("400");
					renderHtml.setMsg("");
					view = "Comment/comment_form";
				}
				try {
					model.addAttribute("comment", comment);
					if (!errors.isEmpty()) {
						model.addAttribute("errors", errors);
					}
					View resolvedView = viewResolver.resolveViewName(view, Locale.FRANCE);
					MockHttpServletResponse mockResp = new MockHttpServletResponse();
					resolvedView.render(model, request, mockResp);
					renderHtml.setRender(mockResp.getContentAsString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				renderHtml.setCode("500");
				renderHtml.setMsg("An error append, please try again! 1");
			}
			
		} catch (NumberFormatException e) {
			renderHtml.setCode("500");
			renderHtml.setMsg("An error append, please try again! 2");
		}				

		return renderHtml;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value="/comment/remove/{id}", method = RequestMethod.DELETE)
	public RenderHtml removeComment(@PathVariable("id") Long id,ModelMap model, HttpSession session, HttpServletRequest request, Principal principal) {
		RenderHtml renderHtml = new RenderHtml();
		User user = userService.findByEmail(principal.getName());
		Comment comment = commentDao.findById(id);
		if (comment != null && (comment.getUser().getId() == user.getId() || comment.getPost().getUser().getId() == user.getId())) {
			commentDao.remove(comment);
			renderHtml.setCode("200");
		} else {
			renderHtml.setCode("400");
		}
		return renderHtml;
	}
}
