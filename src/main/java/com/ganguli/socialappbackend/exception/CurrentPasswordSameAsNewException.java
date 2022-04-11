package com.ganguli.socialappbackend.exception;

public class CurrentPasswordSameAsNewException extends Exception {
	private static final long serialVersionUID = 4L;
	
	public CurrentPasswordSameAsNewException(String message) {
		super(message);
	}
}
