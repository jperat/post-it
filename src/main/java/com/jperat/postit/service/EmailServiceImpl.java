package com.jperat.postit.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
    private TemplateEngine htmlTemplateEngine;

    @Autowired
    private TemplateEngine textTemplateEngine;

    @Autowired
    private TemplateEngine stringTemplateEngine;


    /* 
     * Send plain TEXT mail 
     */
	public void sendTextMail(String[] to, String subject, String from, String template, Context context) throws MessagingException {
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		message.setSubject(subject);
		message.setFrom(from);
		message.setTo(to);
		final String text = this.textTemplateEngine.process(template, context);
		message.setText(text);
		this.mailSender.send(mimeMessage);
	}
	
	/* 
     * Send HTML mail (simple) 
     */
	public void sendSimpleMail(String[] to, String subject, String from, String template, Context context)
        throws MessagingException {

    	final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		
		message.setSubject(subject);
		message.setFrom(from);
		message.setTo(to);
		
		final String text = this.htmlTemplateEngine.process(template, context);
		
		message.setText(text, true);
		
		this.mailSender.send(mimeMessage);
    }
    
	public void sendStringMail(String[] to, String subject, String from, String htmlContent, Context context) throws MessagingException {
    	final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		
		message.setSubject(subject);
		message.setFrom(from);
		message.setTo(to);
		
		final String text = this.stringTemplateEngine.process(htmlContent, context);
		
		message.setText(text, true);
		
		this.mailSender.send(mimeMessage);
    }

//	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
//		try {
//			MimeMessage message = emailSender.createMimeMessage();
//			// pass 'true' to the constructor to create a multipart message
//			MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//			helper.setTo(to);
//			helper.setSubject(subject);
//			helper.setText(text);
//			
//			final String text = this.htmlTemplateEngine.process(template, context);
//
//			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
//			helper.addAttachment("Invoice", file);
//
//			emailSender.send(message);
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//	}
	

}
