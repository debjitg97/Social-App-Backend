package com.ganguli.socialappbackend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ganguli.socialappbackend.dto.SocialUserAddDTO;
import com.ganguli.socialappbackend.dto.SocialUserChangePasswordDTO;
import com.ganguli.socialappbackend.dto.SocialUserDTO;
import com.ganguli.socialappbackend.dto.SocialUserEditDTO;
import com.ganguli.socialappbackend.entity.SocialUser;
import com.ganguli.socialappbackend.exception.BadRequestException;
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
	
	@Value(value = "${currentDetails.sameAsNew}")
	private String currentDetailsSameAsNew;
	
	@Value(value = "${delete.success}")
	private String deleteSuccess;
	
	@Override
	public SocialUserDTO findByUserName(String userName) throws BadRequestException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new BadRequestException(userNameDoesNotExists));
		return new SocialUserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserName());
	}

	@Override
	public SocialUserDTO addUser(SocialUserAddDTO userAddDTO) throws BadRequestException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userAddDTO.getUserName());
		if(userOp.isPresent())
			throw new BadRequestException(userNameAlreadyExists);
		SocialUser user = socialUserRepository.save(new SocialUser(
				null, 
				userAddDTO.getFirstName(), 
				userAddDTO.getLastName(), 
				userAddDTO.getUserName(), 
				passwordEncoder.encode(userAddDTO.getUserPassword())));
		return new SocialUserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserName());
	}

	@Override
	public String editPassword(SocialUserChangePasswordDTO socialUserChangePasswordDTO, String userName)
			throws BadRequestException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new BadRequestException(userNameDoesNotExists));
		if(!passwordEncoder.matches(socialUserChangePasswordDTO.getCurrentPassword(), user.getUserPassword()))
			throw new BadRequestException(currentPasswordIncorrect);
		if(passwordEncoder.matches(socialUserChangePasswordDTO.getNewPassword(), user.getUserPassword()))
			throw new BadRequestException(currentPasswordSameAsNew);
		user.setUserPassword(passwordEncoder.encode(socialUserChangePasswordDTO.getNewPassword()));
		socialUserRepository.save(user);
		return changePasswordSuccess;
	}

	@Override
	public SocialUserDTO editDetails(SocialUserEditDTO socialUserEditDTO, String userName) throws BadRequestException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new BadRequestException(userNameDoesNotExists));
		if(user.getFirstName().equals(socialUserEditDTO.getFirstName()) && user.getLastName().equals(socialUserEditDTO.getLastName()))
			throw new BadRequestException(currentDetailsSameAsNew);
		user.setFirstName(socialUserEditDTO.getFirstName());
		user.setLastName(socialUserEditDTO.getLastName());
		SocialUser savedUser = socialUserRepository.save(user);
		return new SocialUserDTO(savedUser.getUserId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getUserName());
	}

	@Override
	public String deleteUser(String userName) throws BadRequestException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new BadRequestException(userNameDoesNotExists));
		socialUserRepository.delete(user);
		return deleteSuccess;
	}
}
