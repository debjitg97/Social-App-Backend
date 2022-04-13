package com.ganguli.socialappbackend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ganguli.socialappbackend.dto.ErrorResponseDTO;
import com.ganguli.socialappbackend.dto.PostAddDTO;
import com.ganguli.socialappbackend.dto.PostDTO;
import com.ganguli.socialappbackend.exception.BadRequestException;
import com.ganguli.socialappbackend.service.PostService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/post")
public class PostController {
	@Autowired
	private PostService postService;
	
	@ApiOperation(value = "Post from authenticated User")
    @ApiResponses({
    	@ApiResponse(code = 201, message = "Created", response = String.class),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping(value = "/add", produces={"application/json"})
	public ResponseEntity<String> postFromUser(@ApiIgnore final Authentication authentication, @RequestBody @Valid PostAddDTO postAddDTO) throws BadRequestException {
		String userName = authentication.getName();
		return new ResponseEntity<>(postService.addPost(userName, postAddDTO), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Delete Post from authenticated User")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "OK", response = String.class),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping(value = "/delete/{postId}", produces={"application/json"})
	public ResponseEntity<String> deletePostFromUser(@ApiIgnore final Authentication authentication, @PathVariable Integer postId) throws BadRequestException {
		String userName = authentication.getName();
		return new ResponseEntity<>(postService.deletePost(userName, postId), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get Posts from Followed Users by authenticated User")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "OK", response = PostDTO.class, responseContainer = "List"),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.OK)
	@GetMapping(value = "/posts-from-followed/{pageNo}/{size}", produces={"application/json"})
	public ResponseEntity<List<PostDTO>> getPostsByFollowed(@ApiIgnore final Authentication authentication, @PathVariable Integer pageNo, @PathVariable Integer size) throws BadRequestException {
		String userName = authentication.getName();
		Page<PostDTO> postDTOPage = postService.getPostsByFollowed(userName, pageNo, size);
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("x-total-count", Long.toString(postDTOPage.getTotalElements()));
		return new ResponseEntity<>(postDTOPage.getContent(), responseHeaders, HttpStatus.OK);
	}
}
