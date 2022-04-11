package com.ganguli.socialappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:ValidationMessages.properties" })
public class SocialAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialAppBackendApplication.class, args);
	}

}
