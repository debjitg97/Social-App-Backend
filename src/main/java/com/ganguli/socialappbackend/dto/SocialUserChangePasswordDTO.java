package com.ganguli.socialappbackend.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "Change User Password Data Transfer Object")
public class SocialUserChangePasswordDTO {
	@ApiModelProperty(notes = "Current Password", example = "ironManSucks123+")
	@NotNull(message = "{currentPassword.missing}")
	private String currentPassword;
	
	@ApiModelProperty(notes = "New Password", example = "iAmBatman22")
	@NotNull(message = "{newPassword.missing}")
	@Size(min = 8, max = 16, message = "{newPassword.invalidSize}")
	private String newPassword;
}
