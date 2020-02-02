package com.jperat.postit.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jperat.postit.annotation.ValidEmail;
import com.jperat.postit.converter.LongArrayToStringConverter;

@Entity
@Table( name = "users")
public class User {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column( name="user_id" )
	private Long id;
	
	@Column(name = "email", unique = true, nullable = false, length = 255)
	@NotNull(message="The email can not be null")
	@Size(max=255, message="The email must be at maximum {value} characters")
	@ValidEmail()
	private String email;
	
	@Column(name = "password", nullable = false, length = 60)
	@NotNull(message="The password can not be null")
	@Size(min = 8, max = 60, message="The password must be between {min} and {max} characters long")
	private String password;
	
	@Column(name = "username", nullable = false, length = 45)
	@NotNull(message="The username can not be null")
	@Size(max=45, message="The username must be at maximum {value} characters")
	private String username;
	
	@Column(name = "firstname", nullable = false, length = 45)
	@NotNull(message="The firstname can not be null")
	@Size(max=45, message="The firstname must be at maximum {value} characters")
	private String firstname;
	
	@Column(name = "lastname", nullable = false, length = 45)
	@NotNull(message="The lastname can not be null")
	@Size(max=45, message="The lastname must be at maximum {value} characters")
	private String lastname;
	
	@Column(name = "token", nullable = true, length = 255)
	private String token;
	
	@Column(name = "token_expiry_date")
	private Date tokenExpiryDate;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserRole> userRole = new HashSet<UserRole>(0);
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="file_id", nullable = true)
	private File avatar;

	@Column(name="follows")
	@Convert(converter = LongArrayToStringConverter.class)
	private List<Long> follows;
	
	@Column(name="followers")
	@Convert(converter = LongArrayToStringConverter.class)
	private List<Long> followers;
	
	public User () {}
	
	public User (String email, String password, String username, String firstname, String lastname, boolean enabled) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.enabled = enabled;
	}
	
	public User (String email, String password, String username, String firstname, String lastname, boolean enabled, Set<UserRole> userRole) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.enabled = enabled;
		this.userRole = userRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExpiryDate() {
		return tokenExpiryDate;
	}

	public void setTokenExpiryDate(Date tokenExpiryDate) {
		this.tokenExpiryDate = tokenExpiryDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	public File getAvatar() {
		return avatar;
	}

	public void setAvatar(File avatar) {
		this.avatar = avatar;
	}

	public List<Long> getFollows() {
		return follows;
	}

	public void setFollows(List<Long> follows) {
		this.follows = follows;
	}

	public List<Long> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Long> followers) {
		this.followers = followers;
	}

}
