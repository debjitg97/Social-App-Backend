package com.ganguli.socialappbackend.service;

import com.ganguli.socialappbackend.dto.SocialUserAddDTO;
import com.ganguli.socialappbackend.dto.SocialUserChangePasswordDTO;
import com.ganguli.socialappbackend.dto.SocialUserDTO;
import com.ganguli.socialappbackend.dto.SocialUserEditDTO;
import com.ganguli.socialappbackend.exception.BadRequestException;

public interface SocialUserService{
	public SocialUserDTO findByUserName(String userName) throws BadRequestException;
	
	public SocialUserDTO addUser(SocialUserAddDTO userDTO) throws BadRequestException;
	
	public String editPassword(SocialUserChangePasswordDTO socialUserChangePasswordDTO, String userName) throws BadRequestException;
	
	public SocialUserDTO editDetails(SocialUserEditDTO socialUserEditDTO, String userName) throws BadRequestException;
	
	public String deleteUser(String userName) throws BadRequestException;
}
