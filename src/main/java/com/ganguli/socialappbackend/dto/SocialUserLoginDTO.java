package com.ganguli.socialappbackend.dto;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "Login User Data Transfer Object")
public class SocialUserLoginDTO {
	@ApiModelProperty(notes = "User Name", example = "bruceWayne_11")
	@NotNull(message = "{userName.missing}")
	private String userName;
	
	@ApiModelProperty(notes = "User Password", example = "ironManSucks123+")
	@NotNull(message = "{userPassword.missing}")
	private String userPassword;
}
