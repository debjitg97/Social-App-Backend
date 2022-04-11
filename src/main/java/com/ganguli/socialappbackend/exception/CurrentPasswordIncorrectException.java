package com.ganguli.socialappbackend.exception;

public class CurrentPasswordIncorrectException extends Exception {
	private static final long serialVersionUID = 3L;
	
	public CurrentPasswordIncorrectException(String message) {
		super(message);
	}
}
