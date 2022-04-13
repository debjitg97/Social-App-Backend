package com.ganguli.socialappbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ganguli.socialappbackend.entity.Post;
import com.ganguli.socialappbackend.entity.SocialUser;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer>{
	@Query("SELECT p FROM Post p JOIN SocialUser u ON p.socialUser = u WHERE u IN "
			+ "(SELECT u FROM Follow f JOIN SocialUser u ON f.followee = u WHERE f.follower = ?1)")
	public Page<Post> findPostsFromFollowed(SocialUser user, Pageable pageable);
}
