package com.ganguli.socialappbackend.service;

import com.ganguli.socialappbackend.dto.SocialUserAddDTO;
import com.ganguli.socialappbackend.dto.SocialUserChangePasswordDTO;
import com.ganguli.socialappbackend.dto.SocialUserDTO;
import com.ganguli.socialappbackend.exception.CurrentPasswordIncorrectException;
import com.ganguli.socialappbackend.exception.CurrentPasswordSameAsNewException;
import com.ganguli.socialappbackend.exception.UserAlreadyExistsException;
import com.ganguli.socialappbackend.exception.UserNotFoundException;

public interface SocialUserService{
	public SocialUserDTO findByUserName(String userName) throws UserNotFoundException;
	
	public SocialUserDTO addUser(SocialUserAddDTO userDTO) throws UserAlreadyExistsException;
	
	public String changePassword(SocialUserChangePasswordDTO socialUserChangePasswordDTO, String userName) throws UserNotFoundException, CurrentPasswordIncorrectException, CurrentPasswordSameAsNewException;
}
