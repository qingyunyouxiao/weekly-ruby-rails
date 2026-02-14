package com.qingyunyouxiao.sbsn.config;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.qingyunyouxiao.sbsn.dto.CredentialsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest, 
            HttpServletResponse httpServletResponse,   
            FilterChain filterChain) throws ServletException, IOException {
           
        if ("v1/signIn".equals(httpServletRequest.getServletPath())
                && HttpMethod.POST.matches(httpServletRequest.getMethod())) {
            CredentialsDto credentialsDto = MAPPER.readValue(httpServletRequest.getInputStream(), CredentialsDto.class);

           SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(credentialsDto.getLogin(), credentialsDto.getPassword()));
        } 

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}