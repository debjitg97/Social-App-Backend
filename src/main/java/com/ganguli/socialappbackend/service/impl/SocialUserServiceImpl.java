package com.ganguli.socialappbackend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ganguli.socialappbackend.dto.SocialUserAddDTO;
import com.ganguli.socialappbackend.dto.SocialUserDTO;
import com.ganguli.socialappbackend.entity.SocialUser;
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
	
	@Override
	public SocialUserDTO findByUserName(String userName) throws UserNotFoundException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new UserNotFoundException());
		return new SocialUserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserName());
	}

	@Override
	public SocialUserDTO addUser(SocialUserAddDTO userAddDTO) throws UserAlreadyExistsException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userAddDTO.getUserName());
		if(userOp.isPresent())
			throw new UserAlreadyExistsException();
		SocialUser user = socialUserRepository.save(new SocialUser(
				null, 
				userAddDTO.getFirstName(), 
				userAddDTO.getLastName(), 
				userAddDTO.getUserName(), 
				passwordEncoder.encode(userAddDTO.getUserPassword())));
		return new SocialUserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserName());
	}
}
