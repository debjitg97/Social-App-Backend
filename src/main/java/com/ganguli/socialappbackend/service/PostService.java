package com.ganguli.socialappbackend.service;


import org.springframework.data.domain.Page;

import com.ganguli.socialappbackend.dto.PostAddDTO;
import com.ganguli.socialappbackend.dto.PostDTO;
import com.ganguli.socialappbackend.exception.BadRequestException;

public interface PostService {
	public String addPost(String userName, PostAddDTO postAddDTO) throws BadRequestException;
	
	public String deletePost(String userName, Integer postId) throws BadRequestException;
	
	public Page<PostDTO> getPostsByFollowed(String userName, int pageNo, int size) throws BadRequestException;
}
