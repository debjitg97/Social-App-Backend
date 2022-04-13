package com.ganguli.socialappbackend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ganguli.socialappbackend.dto.FollowerAddDTO;
import com.ganguli.socialappbackend.dto.SocialUserDTO;
import com.ganguli.socialappbackend.entity.Follow;
import com.ganguli.socialappbackend.entity.SocialUser;
import com.ganguli.socialappbackend.exception.BadRequestException;
import com.ganguli.socialappbackend.repository.FollowRepository;
import com.ganguli.socialappbackend.repository.SocialUserRepository;
import com.ganguli.socialappbackend.service.FollowService;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
	private FollowRepository followRepository;
	
	@Autowired
	private SocialUserRepository socialUserRepository;
	
	@Value(value = "${userName.doesNotExist}")
	private String userNameDoesNotExists;
	
	@Value(value = "${followeeUserName.doesNotExist}")
	private String followeeUserNameDoesNotExists;
	
	@Value(value = "${unfolloweeUserName.doesNotExist}")
	private String unfolloweeUserNameDoesNotExists;
	
	@Value(value = "${followeeAdd.alreadyExists}")
	private String followedAlready;
	
	@Value(value = "${followeeAdd.success}")
	private String followSuccess;
	
	@Value(value = "${followeeAdd.followOwn}")
	private String followOwn;
	
	@Value(value = "${followeeDelete.unfollowOwn}")
	private String unfollowOwn;
	
	@Value(value = "${followeeDelete.doesNotFollow}")
	private String doesNotFollow;
	
	@Value(value = "${followeeDelete.success}")
	private String unfollowSuccess;

	@Override
	public Page<SocialUserDTO> getFollowees(String userName, int pageNo, int size) throws BadRequestException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new BadRequestException(userNameDoesNotExists));
		Page<SocialUser> socialUserPage = followRepository.findByFollowerUserId(user, PageRequest.of(pageNo, size));
		return socialUserPage.map((socialUser) -> new SocialUserDTO(socialUser.getUserId(), socialUser.getFirstName(), socialUser.getLastName(), socialUser.getUserName()));
	}

	@Override
	public Page<SocialUserDTO> getUsersToFollow(String userName, int pageNo, int size) throws BadRequestException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new BadRequestException(userNameDoesNotExists));
		Page<SocialUser> socialUserPage = followRepository.findToFollowByFolloweeUserId(user, PageRequest.of(pageNo, size));
		return socialUserPage.map((socialUser) -> new SocialUserDTO(socialUser.getUserId(), socialUser.getFirstName(), socialUser.getLastName(), socialUser.getUserName()));
	}

	@Override
	public String followUser(String userName, FollowerAddDTO followerAddDTO) throws BadRequestException {
		if(userName.equals(followerAddDTO.getFolloweeUserName()))
				throw new BadRequestException(followOwn);
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new BadRequestException(userNameDoesNotExists));
		Optional<SocialUser> followeeOp = socialUserRepository.findByUserName(followerAddDTO.getFolloweeUserName());
		SocialUser followee = followeeOp.orElseThrow(() -> new BadRequestException(followeeUserNameDoesNotExists));
		Optional<Follow> followOp = followRepository.findByFolloweeAndFollower(followee, user);
		if(followOp.isPresent())
			throw new BadRequestException(followedAlready);
		Follow follow = new Follow(null, followee, user);
		followRepository.save(follow);
		return followSuccess;
	}

	@Override
	public String unfollowUser(String userName, String followeeUserName) throws BadRequestException {
		if(userName.equals(followeeUserName))
			throw new BadRequestException(unfollowOwn);
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new BadRequestException(userNameDoesNotExists));
		Optional<SocialUser> followeeOp = socialUserRepository.findByUserName(followeeUserName);
		SocialUser followee = followeeOp.orElseThrow(() -> new BadRequestException(unfolloweeUserNameDoesNotExists));
		Optional<Follow> followOp = followRepository.findByFolloweeAndFollower(followee, user);
		Follow follow = followOp.orElseThrow(() -> new BadRequestException(doesNotFollow));
		followRepository.delete(follow);
		return unfollowSuccess;
	}
}
