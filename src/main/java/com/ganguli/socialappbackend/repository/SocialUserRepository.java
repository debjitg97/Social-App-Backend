package com.ganguli.socialappbackend.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ganguli.socialappbackend.entity.SocialUser;

public interface SocialUserRepository extends PagingAndSortingRepository<SocialUser, Integer>{
	public Optional<SocialUser> findByUserName(String userName);
}
