package com.jperat.postit.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jperat.postit.dao.UserDao;
import com.jperat.postit.model.User;

@Controller
public class AccountController {

	UserDao userDao;
	
	public AccountController(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@RequestMapping(path="/account", method=RequestMethod.GET)
	public String indexAction (ModelMap model, HttpSession session, HttpServletRequest request, Principal principal) {
		User principalUser = userDao.findByEmail(principal.getName());
		model.addAttribute("principalUser", principalUser);
		model.addAttribute("user", principalUser);
		return "Account/index";
	}
	
}
