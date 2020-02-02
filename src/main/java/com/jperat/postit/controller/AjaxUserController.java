package com.jperat.postit.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.jperat.postit.jsonview.RenderHtml;
import com.jperat.postit.jsonview.Views;
import com.jperat.postit.model.User;
import com.jperat.postit.service.UserService;

@RestController
public class AjaxUserController {
	
	@Autowired
	UserService userService;

	@JsonView(Views.Public.class)
	@RequestMapping(value="/follow", method = RequestMethod.POST)
	public RenderHtml follow(ModelMap model, HttpSession session, HttpServletRequest request, Principal principal) {
		RenderHtml renderHtml = new RenderHtml();
		User user = userService.findByEmail(principal.getName());
		Long id;
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e) {
			id = null;
		}
		if (id != null && userService.addFollow(user, id))
			renderHtml.setCode("200");
		else
			renderHtml.setCode("400");
		
		return renderHtml;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value="/unfollow", method = RequestMethod.POST)
	public RenderHtml unfollow(ModelMap model, HttpSession session, HttpServletRequest request, Principal principal) {
		RenderHtml renderHtml = new RenderHtml();
		User user = userService.findByEmail(principal.getName());
		Long id;
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e) {
			id = null;
		}
		if (id != null && userService.deleteFollow(user, id))
			renderHtml.setCode("200");
		else
			renderHtml.setCode("400");
		
		return renderHtml;
	}
}
