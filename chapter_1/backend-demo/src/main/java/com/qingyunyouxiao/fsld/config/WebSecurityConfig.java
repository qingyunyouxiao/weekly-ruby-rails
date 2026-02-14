package com.qingyunyouxiao.fsld.config;

import com.qingyunyouxiao.fsld.jwt.JwtAuthFilter;
import com.qingyunyouxiao.fsld.jwt.JwtUtil;
import com.qingyunyouxiao.fsld.repository.UserDtoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDtoRepository userDtoRepository;

    public WebSecurityConfig(JwtUtil jwtUtil, UserDtoRepository userDtoRepository) {
        this.jwtUtil = jwtUtil;
        this.userDtoRepository = userDtoRepository;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, userDtoRepository);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}