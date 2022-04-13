package com.ganguli.socialappbackend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ganguli.socialappbackend.entity.Follow;
import com.ganguli.socialappbackend.entity.SocialUser;

public interface FollowRepository extends PagingAndSortingRepository<Follow, Integer> {
	@Query("SELECT u FROM Follow f JOIN SocialUser u ON f.followee = u WHERE f.follower = ?1")
	public Page<SocialUser> findByFollowerUserId(SocialUser follower, Pageable pageable);
	
	@Query("SELECT u FROM SocialUser u WHERE u NOT IN (SELECT u FROM Follow f JOIN SocialUser u ON f.followee = u WHERE f.follower = ?1) AND u <> ?1")
	public Page<SocialUser> findToFollowByFolloweeUserId(SocialUser followee, Pageable pageable);
	
	public Optional<Follow> findByFolloweeAndFollower(SocialUser followee, SocialUser follower);
}
