package com.app.blog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
//    when unauthorized person tries to access our api
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        response is httpServletResponse object that will be sent for the request we got
        System.out.println("entryPoint");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String errorMessage = "Access Denied !!";
        Map<String,String> errorResponse = new HashMap<>();
        errorResponse.put("error",errorMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
