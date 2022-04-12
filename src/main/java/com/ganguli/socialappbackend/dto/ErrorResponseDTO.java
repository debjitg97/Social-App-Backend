package com.ganguli.socialappbackend.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "Error Response Data Transfer Object")
public class ErrorResponseDTO {
	
	@ApiModelProperty(notes = "List of error messages", example = "[\"Error1\",\"Error2\"]")
	private List<String> messages;
	
	@ApiModelProperty(notes = "Error status code", example = "40X")
	private Integer status;
	
	@ApiModelProperty(notes = "Time Stamp for error", example = "01-01-1990 00:00:00")
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp;
}
