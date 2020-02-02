package com.jperat.postit.model.form;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

	@Column(name = "email", unique = true, nullable = false, length = 255)
	private String email;
	
	@Column(name = "password", nullable = false, length = 60)
	@NotNull(message="The password can not be null")
	@Size(min = 8, max = 60, message="The password must be between {min} and {max} characters long")
	private String password;

	@Column(name = "token", nullable = true, length = 255)
	private String token;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
