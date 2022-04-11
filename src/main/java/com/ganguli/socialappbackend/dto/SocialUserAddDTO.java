package com.ganguli.socialappbackend.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
@ApiModel(description = "Add User Data Transfer Object")
public class SocialUserAddDTO {
	@ApiModelProperty(notes = "First Name", example = "Bruce")
	@NotNull(message = "{firstName.missing}")
	@Size(min = 3, max = 16, message = "{firstName.invalidSize}")
	@Pattern(regexp = "^[a-zA-Z]*", message = "{firstName.invalidPattern}")
	private String firstName;
	
	@ApiModelProperty(notes = "Last Name", example = "Wayne")
	@NotNull(message = "{lastName.missing}")
	@Size(min = 3, max = 16, message = "{lastName.invalidSize}")
	@Pattern(regexp = "^[a-zA-Z]*", message = "{lastName.invalidPattern}")
	private String lastName;
	
	@ApiModelProperty(notes = "User Name", example = "bruceWayne_11")
	@NotNull(message = "{userName.missing}")
	@Size(min = 6, max = 16, message = "{userName.invalidSize}")
	@Pattern(regexp = "^[a-zA-Z0-9_]*", message = "{userName.invalidPattern}")
	private String userName;
	
	@ApiModelProperty(notes = "User Password", example = "ironManSucks123+")
	@NotNull(message = "{userPassword.missing}")
	@Size(min = 8, max = 16, message = "{userPassword.invalidSize}")
	private String userPassword;
	
}
