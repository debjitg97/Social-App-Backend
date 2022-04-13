package com.ganguli.socialappbackend.service;

import org.springframework.data.domain.Page;

import com.ganguli.socialappbackend.dto.FollowerAddDTO;
import com.ganguli.socialappbackend.dto.SocialUserDTO;
import com.ganguli.socialappbackend.exception.BadRequestException;

public interface FollowService {
	public Page<SocialUserDTO> getFollowees(String userName, int pageNo, int size) throws BadRequestException;
	
	public Page<SocialUserDTO> getUsersToFollow(String userName, int pageNo, int size) throws BadRequestException;
	
	public String followUser(String userName, FollowerAddDTO followerAddDTO) throws BadRequestException;
	
	public String unfollowUser(String userName, String followeeUserName) throws BadRequestException;
}
