package com.qingyunyouxiao.fsld.jwt;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.qingyunyouxiao.fsld.repository.UserDtoRepository;
import com.qingyunyouxiao.fsld.dtos.UserDto;

// 必须实现 AuthenticationProvider 接口，才能自定义认证逻辑
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final UserDtoRepository userDtoRepository;
    private final PasswordEncoder passwordEncoder;
    
    // 构造函数注入依赖
    public UserAuthenticationProvider(UserDtoRepository userDtoRepository, PasswordEncoder passwordEncoder) {
        this.userDtoRepository = userDtoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 认证逻辑
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        // 从数据库中获取用户信息
        Optional<UserDto> userOptional = userDtoRepository.findByName(username); 
        if (!userOptional.isPresent() || !passwordEncoder.matches(password, userOptional.get().getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
    }
     
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
