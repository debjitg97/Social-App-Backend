package com.ganguli.socialappbackend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ganguli.socialappbackend.dto.SocialUserAddDTO;
import com.ganguli.socialappbackend.dto.SocialUserChangePasswordDTO;
import com.ganguli.socialappbackend.dto.SocialUserDTO;
import com.ganguli.socialappbackend.entity.SocialUser;
import com.ganguli.socialappbackend.exception.CurrentPasswordIncorrectException;
import com.ganguli.socialappbackend.exception.CurrentPasswordSameAsNewException;
import com.ganguli.socialappbackend.exception.UserAlreadyExistsException;
import com.ganguli.socialappbackend.exception.UserNotFoundException;
import com.ganguli.socialappbackend.repository.SocialUserRepository;
import com.ganguli.socialappbackend.service.SocialUserService;

@Service
public class SocialUserServiceImpl implements SocialUserService{
	
	@Autowired
	private SocialUserRepository socialUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value(value = "${changePassword.success}")
	private String changePasswordSuccess;
	
	@Value(value = "${userName.alreadyExists}")
	private String userNameAlreadyExists;
	
	@Value(value = "${userName.doesNotExist}")
	private String userNameDoesNotExists;
	
	@Value(value = "${currentPassword.incorrect}")
	private String currentPasswordIncorrect;
	
	@Value(value = "${currentPassword.sameAsNew}")
	private String currentPasswordSameAsNew;
	
	@Override
	public SocialUserDTO findByUserName(String userName) throws UserNotFoundException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new UserNotFoundException(userNameDoesNotExists));
		return new SocialUserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserName());
	}

	@Override
	public SocialUserDTO addUser(SocialUserAddDTO userAddDTO) throws UserAlreadyExistsException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userAddDTO.getUserName());
		if(userOp.isPresent())
			throw new UserAlreadyExistsException(userNameAlreadyExists);
		SocialUser user = socialUserRepository.save(new SocialUser(
				null, 
				userAddDTO.getFirstName(), 
				userAddDTO.getLastName(), 
				userAddDTO.getUserName(), 
				passwordEncoder.encode(userAddDTO.getUserPassword())));
		return new SocialUserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserName());
	}

	@Override
	public String changePassword(SocialUserChangePasswordDTO socialUserChangePasswordDTO, String userName)
			throws UserNotFoundException, CurrentPasswordIncorrectException, CurrentPasswordSameAsNewException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new UserNotFoundException(userNameDoesNotExists));
		if(!passwordEncoder.matches(socialUserChangePasswordDTO.getCurrentPassword(), user.getUserPassword()))
			throw new CurrentPasswordIncorrectException(currentPasswordIncorrect);
		if(passwordEncoder.matches(socialUserChangePasswordDTO.getNewPassword(), user.getUserPassword()))
			throw new CurrentPasswordSameAsNewException(currentPasswordSameAsNew);
		user.setUserPassword(passwordEncoder.encode(socialUserChangePasswordDTO.getNewPassword()));
		socialUserRepository.save(user);
		return changePasswordSuccess;
	}
}
