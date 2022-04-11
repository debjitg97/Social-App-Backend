package com.ganguli.socialappbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	@RequestMapping("/")
	private String getDefaultHTML() {
		return "redirect:/api/documentation/swagger-ui/";
	}
}
