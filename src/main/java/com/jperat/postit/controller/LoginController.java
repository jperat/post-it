package com.jperat.postit.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.Context;

import com.jperat.postit.dao.UserDao;
import com.jperat.postit.exception.EmailExistsException;
import com.jperat.postit.exception.NotFoundEmailException;
import com.jperat.postit.model.User;
import com.jperat.postit.service.EmailService;
import com.jperat.postit.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDao userDao;

	@Autowired
	EmailService emailService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "Login/login";
	}
	
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String forgotPassword(ModelMap model) {
		model.addAttribute("user", new User());
		return "Login/forgotpassword";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public String forgotPassword(@Valid User user, BindingResult result, ModelMap model, HttpServletRequest request,
			final Locale locale) {
		List<String> errors = new ArrayList<String>();
		model.addAttribute("errors", errors);

		try {
			user = userService.forgotPasswordReset(user.getEmail());
			String[] to = { user.getEmail() };
			String subject = "Reset password";
			String from = "no-reply@exmeple.com";
			String template = "html/forgotpassword";
			Context context = new Context(locale);
			context.setVariable("user", user);
			StringBuffer url = request.getRequestURL();
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
			context.setVariable("resetPasswordUrl", base+"/resetpassword");
			try {
				emailService.sendSimpleMail(to, subject, from, template, context);
				model.addAttribute("user", user);
				model.addAttribute("success", "An email has been send for reset password.");
			} catch (MessagingException e) {
				errors.add("An herror hapened! Please try again");
				e.printStackTrace();
			}
		} catch (NotFoundEmailException e) {
			errors.add("No account for this email");
		}
		
		return "Login/forgotpassword";
	}
	
	@RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
	public String resetPassword(ModelMap model, HttpServletRequest request){
		String token = request.getParameter("token");
		String email = request.getParameter("email");
		User user = null;
		System.out.println(token);
		if (token !=  null && email != null) {
			user = userDao.findByTokenAndEmail(token, email);
		}
		if (user == null) {
			model.addAttribute("tokenError", true);
		}
		model.addAttribute("user", user);
		return "Login/resetpassword";		
	}
	
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public String resetPassword(@Valid com.jperat.postit.model.form.User userForm, BindingResult result, ModelMap model, HttpServletRequest request){		
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			return "resetpassword";
		}
		User user = userDao.findByTokenAndEmail(userForm.getToken(), userForm.getEmail());
		model.addAttribute("user", user);
		if (user == null) {
			model.addAttribute("tokenError", true);
			return "resetpassword";
		}
		userDao.updatePassword(user, userForm.getPassword());
		try {
			request.login(userForm.getEmail(), userForm.getPassword());
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			System.out.println("Auto login error after register");
			e.printStackTrace();
		}
		return "redirect:/";	
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showForm(ModelMap model) {
		model.addAttribute("user", new User());
		return "Login/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPage(@Valid User user, BindingResult result, ModelMap model, HttpServletRequest request) {
		model.addAttribute("user", user);
		User registered = null;
		String formPassword = user.getPassword();

		if (!result.hasErrors()) {
			registered = createUserAccount(user);
			if (registered == null) {
				result.rejectValue("email", "This email is allready used");
			}
		}

		

		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			return "Login/register";
		} else {
			try {
				request.login(registered.getEmail(), formPassword);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				System.out.println("Auto login error after register");
				e.printStackTrace();
			}
			return "redirect:/";
		}

	}

	private User createUserAccount(User user) {
		try {
			return userService.registerNewUserAccount(user);
		} catch (EmailExistsException e) {
			return null;
		}
	}
}
