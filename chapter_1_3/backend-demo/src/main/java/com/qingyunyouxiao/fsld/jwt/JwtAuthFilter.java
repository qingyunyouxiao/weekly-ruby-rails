package com.qingyunyouxiao.fsld.jwt;

import com.qingyunyouxiao.fsld.repository.UserDtoRepository;
import com.qingyunyouxiao.fsld.dtos.UserDto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDtoRepository userDtoRepository;
    
    public JwtAuthFilter(JwtUtil jwtUtil, UserDtoRepository userDtoRepository) {
        this.jwtUtil = jwtUtil;
        this.userDtoRepository = userDtoRepository;
    }
    
    @Override
    protected void doFilterInternal (
        @NonNull HttpServletRequest httpServletRequest,
        @NonNull HttpServletResponse httpServletResponse,
        @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");
            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {
                    // 获取token
                    String token = authElements[1];
                    
                    // 验证token
                    if (jwtUtil.validateToken(token)) {
                        String username = jwtUtil.getUsernameFromToken(token);
                        
                        // 从数据库中获取用户信息
                        Optional<UserDto> userOptional = userDtoRepository.findByName(username); 
                        if (userOptional.isPresent()) {
                            // 构造认证信息
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                    
                } catch (Exception e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }  
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
