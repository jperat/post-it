package com.jperat.postit.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
public class SearchController {

	@Autowired
	UserDao userDao;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String searchPage(ModelMap model, HttpSession session, HttpServletRequest request, Principal principal) {
		List<User> users;
		String q = request.getParameter("q");
		if (q.length() > 0) {
			User principalUser = userDao.findByEmail(principal.getName());
			model.addAttribute("principalUser", principalUser);
			users = userDao.search(q, 0);
		} else {
			users = new ArrayList<User>();
		}
		model.addAttribute("users", users);
		return "Search/index";
	}
}
