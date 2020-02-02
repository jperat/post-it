package com.jperat.postit.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jperat.postit.model.User;
import com.jperat.postit.service.UserService;;

@Component
public class UserFormValidator implements Validator{

	@Autowired
	@Qualifier("emailValidator")
	EmailValidator emailValidator;
	
	@Autowired
	UserService userService;
	

	public boolean supports(Class<?> arg0) {
		return User.class.equals(arg0);
	}

	public void validate(Object obj, Errors errors) {
		User user = (User) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.userForm.username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "NotEmpty.userForm.firstname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "NotEmpty.userForm.lastname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
		
//		if (!emailValidator.valid(user.getEmail())) {
//			errors.rejectValue("email", "Pattern.userForm.email");
//		}
		
		if (emailExist(user.getEmail())) {
			errors.rejectValue("email", "AllReadyExist.userForm.email");
		}
		
		System.out.println("test");
	}
		
	private boolean emailExist(String email) {		
		User user = userService.findByEmail(email);
		if (user == null)
			return false;		
		return true;
	}
	
	
}
