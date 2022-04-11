package com.ganguli.socialappbackend.exception;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 2L;
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
