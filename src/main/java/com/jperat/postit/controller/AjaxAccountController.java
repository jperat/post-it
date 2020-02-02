package com.jperat.postit.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.fasterxml.jackson.annotation.JsonView;
import com.jperat.postit.dao.FileDao;
import com.jperat.postit.dao.UserDao;
import com.jperat.postit.jsonview.RenderHtml;
import com.jperat.postit.jsonview.Views;
import com.jperat.postit.model.User;
import com.jperat.postit.service.StorageService;
import com.jperat.postit.service.UserService;;

@RestController
public class AjaxAccountController {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	FileDao fileDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	ViewResolver viewResolver;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@JsonView(Views.Public.class)
	@RequestMapping(value="/account/identity", method = RequestMethod.POST)
	public RenderHtml identity (@ModelAttribute User user, BindingResult result, ModelMap model, HttpServletRequest request, Principal principal) {
		RenderHtml renderHtml = new RenderHtml();
		User principalUser = userDao.findByEmail(principal.getName());
		List<ObjectError> errors = new ArrayList<ObjectError>();
		errors.addAll(result.getAllErrors());
		
		if (errors.isEmpty()) {
			principalUser.setUsername(user.getUsername());
			principalUser.setFirstname(user.getFirstname());
			principalUser.setLastname(user.getLastname());
			userDao.updateOrSave(principalUser);
			model.addAttribute("success", "Change saved");
		} else {
			model.addAttribute("errors", errors);
		}
		
		model.addAttribute("user", user);
		
		try {
			View resolvedView = viewResolver.resolveViewName("Account/identity_form", Locale.FRANCE);
			MockHttpServletResponse mockResp = new MockHttpServletResponse();
			resolvedView.render(model, request, mockResp);
			renderHtml.setRender(mockResp.getContentAsString());
			renderHtml.setCode("200");
		} catch (Exception e) {
			e.printStackTrace();
			renderHtml.setCode("500");
			renderHtml.setMsg("An error happened!");
		}
		
		return renderHtml;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value="/account/email", method = RequestMethod.POST)
	public RenderHtml email (@ModelAttribute User user, BindingResult result, ModelMap model, HttpServletRequest request, Principal principal) {
		RenderHtml renderHtml = new RenderHtml();
		User principalUser = userDao.findByEmail(principal.getName());
		List<ObjectError> errors = new ArrayList<ObjectError>();
		errors.addAll(result.getAllErrors());
		if (userService.findByEmail(user.getEmail()) != null) {
			errors.add(new ObjectError("emailAllReadyExist", "This email is allready used!"));
		}
		
		if (errors.isEmpty()) {
			principalUser.setEmail(user.getEmail());
			userDao.updateOrSave(principalUser);
			model.addAttribute("success", "Change saved");
		} else {
			model.addAttribute("errors", errors);
		}
		
		model.addAttribute("user", user);
		
		try {
			View resolvedView = viewResolver.resolveViewName("Account/email_form", Locale.FRANCE);
			MockHttpServletResponse mockResp = new MockHttpServletResponse();
			resolvedView.render(model, request, mockResp);
			renderHtml.setRender(mockResp.getContentAsString());
			renderHtml.setCode("200");
		} catch (Exception e) {
			e.printStackTrace();
			renderHtml.setCode("500");
			renderHtml.setMsg("An error happened!");
		}
		
		return renderHtml;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value="/account/password", method = RequestMethod.POST)
	public RenderHtml password(ModelMap model, HttpServletRequest request, Principal principal) {
		RenderHtml renderHtml = new RenderHtml();
		User principalUser = userDao.findByEmail(principal.getName());
		List<ObjectError> errors = new ArrayList<ObjectError>();
		
		String oldPassword = request.getParameter("old_password");
		String newPassword = request.getParameter("new_password");
		
		if (!passwordEncoder.matches(oldPassword, principalUser.getPassword())) {
			errors.add(new ObjectError("Wrong password", "You wright a wrong password"));
		}
		if (newPassword.length() < 8) {
			errors.add(new ObjectError("short password", "Too short password (8 cheracters min)"));
		}
		
		if (errors.isEmpty()) {
			principalUser.setPassword(passwordEncoder.encode(newPassword));
			userDao.updateOrSave(principalUser);
			model.addAttribute("success", "Change saved");
		} else {
			model.addAttribute("errors", errors);
		}
		
		
		try {
			View resolvedView = viewResolver.resolveViewName("Account/password_form", Locale.FRANCE);
			MockHttpServletResponse mockResp = new MockHttpServletResponse();
			resolvedView.render(model, request, mockResp);
			renderHtml.setRender(mockResp.getContentAsString());
			renderHtml.setCode("200");
		} catch (Exception e) {
			e.printStackTrace();
			renderHtml.setCode("500");
			renderHtml.setMsg("An error happened!");
		}
		
		return renderHtml;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value="/account/avatar", method = RequestMethod.POST)
	public RenderHtml avatar (ModelMap model, HttpServletRequest request, Principal principal, @RequestParam("avatar") MultipartFile avatar) {
		RenderHtml renderHtml = new RenderHtml();
		User principalUser = userDao.findByEmail(principal.getName());
		List<ObjectError> errors = new ArrayList<ObjectError>();
		if (principalUser.getAvatar() != null && !storageService.deleteUploadFile(principalUser.getAvatar().getName())) {
			errors.add(new ObjectError("avatar", "The new avatar can't be upload2!"));
		}
		if (errors.isEmpty()) {
			principalUser.setAvatar(storageService.store(avatar));
			if (principalUser.getAvatar() == null) {
				errors.add(new ObjectError("avatar", "The new avatar can't be upload!"));
			} 
		}
		
		if (errors.isEmpty()) {
			userDao.updateOrSave(principalUser);
			model.addAttribute("success", "Change saved");
		} else {
			model.addAttribute("errors", errors);
		}
		
		model.addAttribute("user", principalUser);
		
		try {
			View resolvedView = viewResolver.resolveViewName("Account/avatar_form", Locale.FRANCE);
			MockHttpServletResponse mockResp = new MockHttpServletResponse();
			resolvedView.render(model, request, mockResp);
			renderHtml.setRender(mockResp.getContentAsString());
			renderHtml.setCode("200");
		} catch (Exception e) {
			e.printStackTrace();
			renderHtml.setCode("500");
			renderHtml.setMsg("An error happened!");
		}
		
		return renderHtml;
	}
}
