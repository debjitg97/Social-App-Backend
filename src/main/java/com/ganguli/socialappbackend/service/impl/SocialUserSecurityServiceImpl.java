package com.ganguli.socialappbackend.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ganguli.socialappbackend.entity.SocialUser;
import com.ganguli.socialappbackend.repository.SocialUserRepository;
import com.ganguli.socialappbackend.service.SocialUserSecurityService;

@Service
public class SocialUserSecurityServiceImpl implements SocialUserSecurityService {
	
	@Autowired
	private SocialUserRepository socialUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new UsernameNotFoundException(userName));
		return new User(user.getUserName(), user.getUserPassword(), new ArrayList<>());
	}

}
