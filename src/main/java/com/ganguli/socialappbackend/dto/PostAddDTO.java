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
@ApiModel(description = "Add Post Data Transfer Object")
public class PostAddDTO {
	@ApiModelProperty(notes = "Title", example = "I am vengeance")
	@NotNull(message = "{title.missing}")
	@Size(min = 3, max = 32, message = "{title.invalidSize}")
	private String title;
	
	@ApiModelProperty(notes = "Description", example = "I am vengeance, I am the night, I am Batman.")
	@NotNull(message = "{description.missing}")
	@Size(min = 3, max = 320, message = "{description.invalidSize}")
	private String description;
}
