package com.ganguli.socialappbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "User Data Transfer Object")
public class SocialUserDTO {
	@ApiModelProperty(notes = "User ID", example = "11")
	private Integer userId;
	
	@ApiModelProperty(notes = "First Name", example = "Bruce")
	private String firstName;
	
	@ApiModelProperty(notes = "Last Name", example = "Wayne")
	private String lastName;
	
	@ApiModelProperty(notes = "User Name", example = "bruceWayne_11")
	private String userName;
}
