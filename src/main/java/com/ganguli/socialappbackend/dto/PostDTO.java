package com.ganguli.socialappbackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "Post Data Transfer Object")
public class PostDTO {
	@ApiModelProperty(notes = "Post ID", example = "1")
	private Integer postId;
	
	@ApiModelProperty(notes = "Title", example = "I am vengeance")
	private String title;
	
	@ApiModelProperty(notes = "Description", example = "I am vengeance, I am the night, I am Batman.")
	private String description;
	
	@ApiModelProperty(notes = "User Details")
	private SocialUserDTO socialUserDTO;
}
