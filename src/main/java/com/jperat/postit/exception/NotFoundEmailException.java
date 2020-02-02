package com.jperat.postit.exception;

public class NotFoundEmailException extends Exception{

	public NotFoundEmailException(){
		
	}
	
	public NotFoundEmailException(String message) {
		super(message);
	}
}
