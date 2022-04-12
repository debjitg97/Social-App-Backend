package com.ganguli.socialappbackend.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "User Edit Data Transfer Object")
public class SocialUserEditDTO {
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
}
