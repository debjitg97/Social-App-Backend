package com.ganguli.socialappbackend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ganguli.socialappbackend.dto.PostAddDTO;
import com.ganguli.socialappbackend.dto.PostDTO;
import com.ganguli.socialappbackend.dto.SocialUserDTO;
import com.ganguli.socialappbackend.entity.Post;
import com.ganguli.socialappbackend.entity.SocialUser;
import com.ganguli.socialappbackend.exception.BadRequestException;
import com.ganguli.socialappbackend.repository.PostRepository;
import com.ganguli.socialappbackend.repository.SocialUserRepository;
import com.ganguli.socialappbackend.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private SocialUserRepository socialUserRepository;
	
	@Value(value = "${userName.doesNotExist}")
	private String userNameDoesNotExists;
	
	@Value(value = "${post.success}")
	private String postSuccess;
	
	@Value(value = "${post.doesNotExist}")
	private String postDoesNotExist;
	
	@Value(value = "${post.delete.wrongUser}")
	private String postDeleteWrongUser;
	
	@Value(value = "${post.delete.success}")
	private String postDeleteSuccess;
	
	@Override
	public String addPost(String userName, PostAddDTO postAddDTO) throws BadRequestException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new BadRequestException(userNameDoesNotExists));
		Post post = new Post(null, postAddDTO.getTitle(), postAddDTO.getDescription(), user);
		postRepository.save(post);
		return postSuccess;
	}

	@Override
	public String deletePost(String userName, Integer postId) throws BadRequestException {
		Optional<Post> postOp = postRepository.findById(postId);
		Post post = postOp.orElseThrow(() -> new BadRequestException(postDoesNotExist));
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new BadRequestException(userNameDoesNotExists));
		if(!post.getSocialUser().equals(user))
			throw new BadRequestException(postDeleteWrongUser);
		postRepository.delete(post);
		return postDeleteSuccess;
	}

	@Override
	public Page<PostDTO> getPostsByFollowed(String userName, int pageNo, int size) throws BadRequestException {
		Optional<SocialUser> userOp = socialUserRepository.findByUserName(userName);
		SocialUser user = userOp.orElseThrow(() -> new BadRequestException(userNameDoesNotExists));
		Page<Post> postPage = postRepository.findPostsFromFollowed(user, PageRequest.of(pageNo, size, Sort.by("postId").descending()));
		return postPage.map((post) -> new PostDTO(post.getPostId(), post.getTitle(), post.getDescription(), 
				new SocialUserDTO(post.getSocialUser().getUserId(), post.getSocialUser().getFirstName(), post.getSocialUser().getLastName(),
						post.getSocialUser().getUserName())));
	}

}
