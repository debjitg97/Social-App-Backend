package com.ganguli.socialappbackend.config;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganguli.socialappbackend.dto.ErrorResponseDTO;

@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException ex) throws IOException, ServletException {
		List<String> messages = new ArrayList<>();
		messages.add(ex.getMessage());
    	ErrorResponseDTO response = new ErrorResponseDTO(messages, HttpStatus.UNAUTHORIZED.value(), new Date());
        OutputStream out = httpServletResponse.getOutputStream();
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, response);
        out.flush();
    }
}