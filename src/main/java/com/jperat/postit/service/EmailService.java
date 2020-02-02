package com.jperat.postit.service;

import javax.mail.MessagingException;

import org.thymeleaf.context.Context;

public interface EmailService {
	
	public void sendTextMail(String[] to, String subject, String from, String template, Context context) throws MessagingException;
	
	public void sendSimpleMail(String[] to, String subject, String from, String template, Context context) throws MessagingException;
	
	public void sendStringMail(String[] to, String subject, String from, String htmlContent, Context context) throws MessagingException;
}
