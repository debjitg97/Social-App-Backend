package com.ganguli.socialappbackend.controller.advice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ganguli.socialappbackend.dto.ErrorResponseDTO;
import com.ganguli.socialappbackend.exception.UserAlreadyExistsException;
import com.ganguli.socialappbackend.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalControllerAdvice {
	
	@Value(value = "${userName.alreadyExists}")
	private String userNameAlreadyExists;
	
	@Value(value = "${userName.doesNotExist}")
	private String userNameDoesNotExists;
	
	@ExceptionHandler(value = { MethodArgumentNotValidException.class } )
	public ResponseEntity<ErrorResponseDTO> badRequest(MethodArgumentNotValidException ex) {
		List<String> messages = new ArrayList<>();
		for(ObjectError error: ex.getAllErrors())
			messages.add(error.getDefaultMessage());
		return new ResponseEntity<>(new ErrorResponseDTO(messages, HttpStatus.BAD_REQUEST.value(), new Date()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { UserAlreadyExistsException.class })
	public ResponseEntity<ErrorResponseDTO> userNameAlreadyExists(UserAlreadyExistsException ex) {
		List<String> messages = new ArrayList<>();
		messages.add(userNameAlreadyExists);
		return new ResponseEntity<>(new ErrorResponseDTO(messages, HttpStatus.BAD_REQUEST.value(), new Date()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { UserNotFoundException.class })
	public ResponseEntity<ErrorResponseDTO> userNamenotFound(UserNotFoundException ex) {
		List<String> messages = new ArrayList<>();
		messages.add(userNameDoesNotExists);
		return new ResponseEntity<>(new ErrorResponseDTO(messages, HttpStatus.NOT_FOUND.value(), new Date()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { AuthenticationException.class })
	public ResponseEntity<ErrorResponseDTO> badCredentials(AuthenticationException ex) {
		List<String> messages = new ArrayList<>();
		messages.add(ex.getMessage());
		return new ResponseEntity<>(new ErrorResponseDTO(messages, HttpStatus.UNAUTHORIZED.value(), new Date()), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ErrorResponseDTO> generalException(Exception ex) {
		List<String> messages = new ArrayList<>();
		messages.add(ex.getMessage());
		return new ResponseEntity<>(new ErrorResponseDTO(messages, HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
