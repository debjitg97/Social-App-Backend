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
@ApiModel(description = "Add Follower Data Transfer Object")
public class FollowerAddDTO {
	@ApiModelProperty(notes = "Followee User Name", example = "thomasWayne_11")
	@NotNull(message = "{followeeUserName.missing}")
	private String followeeUserName;
}
