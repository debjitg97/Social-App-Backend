package com.ganguli.socialappbackend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ganguli.socialappbackend.dto.ErrorResponseDTO;
import com.ganguli.socialappbackend.dto.SocialUserAddDTO;
import com.ganguli.socialappbackend.dto.SocialUserChangePasswordDTO;
import com.ganguli.socialappbackend.dto.SocialUserDTO;
import com.ganguli.socialappbackend.dto.SocialUserEditDTO;
import com.ganguli.socialappbackend.dto.SocialUserLoginDTO;
import com.ganguli.socialappbackend.exception.BadRequestException;
import com.ganguli.socialappbackend.service.SocialUserService;
import com.ganguli.socialappbackend.util.JWTUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/users")
public class SocialUserController {
	@Autowired
	private SocialUserService socialUserService;
	
    @Autowired
    private AuthenticationManager authenticationManager; 
	
    @ApiOperation(value = "Login an User")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "OK", response = String.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping(value = "/login", produces={"application/json"})
    public ResponseEntity<String> loginUser(@Valid @RequestBody SocialUserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUserName(), userLoginDTO.getUserPassword()));
        return new ResponseEntity<>(JWTUtil.generateToken(userLoginDTO.getUserName()), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Add an User")
    @ApiResponses({
    	@ApiResponse(code = 201, message = "Created", response = SocialUserDTO.class),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping(value = "/add", produces={"application/json"})
	public ResponseEntity<SocialUserDTO> addUser(@Valid @RequestBody SocialUserAddDTO userAddDTO)throws BadRequestException {
		SocialUserDTO socialUserDTO = socialUserService.addUser(userAddDTO);
		return new ResponseEntity<>(socialUserDTO, HttpStatus.CREATED);
	}
	
    @ApiOperation(value = "Get User Details for authenticated User")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "OK", response = SocialUserDTO.class),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.OK)
	@GetMapping(value = "/details", produces={"application/json"})
	public ResponseEntity<SocialUserDTO> getAuthenticatedUser(@ApiIgnore final Authentication authentication) throws BadRequestException {
		String userName = authentication.getName();
		return new ResponseEntity<>(this.socialUserService.findByUserName(userName), HttpStatus.OK);
	}
    
    @ApiOperation(value = "Get User Details for given User")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "OK", response = SocialUserDTO.class),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.OK)
	@GetMapping(value = "/details/{userName}", produces={"application/json"})
	public ResponseEntity<SocialUserDTO> getUser(@PathVariable String userName) throws BadRequestException {
		return new ResponseEntity<>(this.socialUserService.findByUserName(userName), HttpStatus.OK);
	}
    
    @ApiOperation(value = "Edit Password for authenticated User")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "OK", response = String.class),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.OK)
    @PatchMapping(value = "/edit-password", produces={"application/json"})
    public ResponseEntity<String> editPassword(@Valid @RequestBody SocialUserChangePasswordDTO socialUserChangePasswordDTO, 
    		@ApiIgnore final Authentication authentication) 
    				throws BadRequestException {
        String result = socialUserService.editPassword(socialUserChangePasswordDTO, authentication.getName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Edit Details for authenticated User")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "OK", response = SocialUserDTO.class),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.OK)
    @PatchMapping(value = "/edit-details", produces={"application/json"})
    public ResponseEntity<SocialUserDTO> editDetails(@Valid @RequestBody SocialUserEditDTO socialUserEditDTO, 
    		@ApiIgnore final Authentication authentication) 
    				throws BadRequestException {
        SocialUserDTO userDTO = socialUserService.editDetails(socialUserEditDTO, authentication.getName());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Delete authenticated User")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "OK", response = String.class),
    	@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseDTO.class),
    	@ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponseDTO.class)
    })
    @ResponseStatus(code = HttpStatus.OK)
    @DeleteMapping(value = "/delete", produces={"application/json"})
    public ResponseEntity<String> deleteUser(@ApiIgnore final Authentication authentication) throws BadRequestException {
        String result = socialUserService.deleteUser(authentication.getName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
