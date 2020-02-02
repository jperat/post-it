package com.jperat.postit.model;

import java.awt.print.Printable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.springframework.web.multipart.MultipartFile;

import com.jperat.postit.converter.LongArrayToStringConverter;

@Entity
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "file_id", nullable = true)
	private File picture;
	
	@Column(name = "message", length = 255, nullable = true)
	@Size(max = 255)
	private String message;
	
	@OneToMany(fetch = FetchType.EAGER,cascade={CascadeType.REMOVE, CascadeType.MERGE}, mappedBy="post")
	@OrderBy("date DESC")
	@BatchSize(size=3)
	private List<Comment> comments;
	
	@Column(name="likes")
	@Convert(converter = LongArrayToStringConverter.class)
	private List<Long> likes;
	
	@Column(name = "date")
	private Date date;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File getPicture() {
		return picture;
	}

	public void setPicture(File picture) {
		this.picture = picture;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Long> getLikes() {
		return likes;
	}

	public void setLikes(List<Long> likes) {
		this.likes = likes;
	}
	
	public boolean isLike(Long id) {
		if (id == null)
			System.out.println("id = null");
		
		if (likes.isEmpty()) {
			System.out.println("isEmpty");
			return false;
		} 
		for (Long like : likes) {
			System.out.println(like);
			if (like.longValue() == id.longValue())
				return true;
		}
		System.out.println("none");
		return false;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
}
