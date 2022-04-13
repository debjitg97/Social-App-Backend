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
import com.ganguli.socialappbackend.dto.FollowerAddDTO;
import com.ganguli.socialappbackend.dto.SocialUserDTO;
import com.ganguli.socialappbackend.exception.BadRequestException;
import com.ganguli.socialappbackend.service.FollowService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
	@Autowired
	private FollowService followService;
	
	@ApiOperation(value = "Get Users being Followed by authenticated User")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "OK", response = SocialUserDTO.class, responseContainer = "List"),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.OK)
	@GetMapping(value = "/following/{pageNo}/{size}", produces={"application/json"})
	public ResponseEntity<List<SocialUserDTO>> getFollowees(@ApiIgnore final Authentication authentication, @PathVariable Integer pageNo, @PathVariable Integer size) throws BadRequestException {
		String userName = authentication.getName();
		Page<SocialUserDTO> socialUserDTOPage = followService.getFollowees(userName, pageNo, size);
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("x-total-count", Long.toString(socialUserDTOPage.getTotalElements()));
		return new ResponseEntity<>(socialUserDTOPage.getContent(), responseHeaders, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get Users to Follow for authenticated User")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "OK", response = SocialUserDTO.class, responseContainer = "List"),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.OK)
	@GetMapping(value = "/can-follow/{pageNo}/{size}", produces={"application/json"})
	public ResponseEntity<List<SocialUserDTO>> getUsersToFollow(@ApiIgnore final Authentication authentication, @PathVariable Integer pageNo, @PathVariable Integer size) throws BadRequestException {
		String userName = authentication.getName();
		Page<SocialUserDTO> socialUserDTOPage = followService.getUsersToFollow(userName, pageNo, size);
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("x-total-count", Long.toString(socialUserDTOPage.getTotalElements()));
		return new ResponseEntity<>(socialUserDTOPage.getContent(), responseHeaders, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Follow given User from authenticated User")
    @ApiResponses({
    	@ApiResponse(code = 201, message = "Created", response = String.class),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping(value = "/add", produces={"application/json"})
	public ResponseEntity<String> followAnUser(@ApiIgnore final Authentication authentication, @RequestBody @Valid FollowerAddDTO followerAddDTO) throws BadRequestException {
		String userName = authentication.getName();
		return new ResponseEntity<>(followService.followUser(userName, followerAddDTO), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Unfollow given User from authenticated User")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "OK", response = String.class),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping(value = "/delete/{followeeUserName}", produces={"application/json"})
	public ResponseEntity<String> unfollowAnUser(@ApiIgnore final Authentication authentication, @PathVariable String followeeUserName) throws BadRequestException {
		String userName = authentication.getName();
		return new ResponseEntity<>(followService.unfollowUser(userName, followeeUserName), HttpStatus.OK);
	}
}
