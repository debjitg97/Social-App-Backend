package com.ganguli.socialappbackend.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public Docket apiDocket1() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.securityContexts(Arrays.asList(securityContext()))
			    .securitySchemes(Arrays.asList(apiKey()))
	            .groupName("User")
	            .select().apis(RequestHandlerSelectors.basePackage("com.ganguli.socialappbackend.controller"))
	            .paths(PathSelectors.ant("/api/users/**"))
	            .build();
	}
	
	@Bean
	public Docket apiDocket2() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.securityContexts(Arrays.asList(securityContext()))
			    .securitySchemes(Arrays.asList(apiKey()))
	            .groupName("Follow")
	            .select().apis(RequestHandlerSelectors.basePackage("com.ganguli.socialappbackend.controller"))
	            .paths(PathSelectors.ant("/api/follow/**"))
	            .build();
	}
	
	@Bean
	public Docket apiDocket3() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.securityContexts(Arrays.asList(securityContext()))
			    .securitySchemes(Arrays.asList(apiKey()))
	            .groupName("Post")
	            .select().apis(RequestHandlerSelectors.basePackage("com.ganguli.socialappbackend.controller"))
	            .paths(PathSelectors.ant("/api/post/**"))
	            .build();
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo("API for Social App", "Backend Application for the Social App", "V1",  "", new Contact("Debjit Ganguli", "", "dganguli1997@gmail.com"), "", "", Collections.emptyList());
	}
	
	private ApiKey apiKey() { 
	    return new ApiKey("JWT", "Authorization", "header"); 
	}
	
	private SecurityContext securityContext() { 
	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 

	private List<SecurityReference> defaultAuth() { 
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope; 
	    return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
	}
}
