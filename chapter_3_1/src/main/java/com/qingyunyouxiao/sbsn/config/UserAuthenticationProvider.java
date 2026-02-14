package com.qingyunyouxiao.sbsn.config;

import com.qingyunyouxiao.sbsn.dto.UserDto;
import com.qingyunyouxiao.sbsn.dto.CredentialsDto;
import com.qingyunyouxiao.sbsn.services.AuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class UserAuthenticationProvider {

    private final String secretKey;
    private final AuthenticationService authenticationService;
    
    // 仅注入服务，密钥通过配置+初始化获取
    public UserAuthenticationProvider(
            @Value("${security.jwt.token.secret-key}") String secretKey, 
            AuthenticationService authenticationService) {
        this.secretKey = secretKey;
        this.authenticationService = authenticationService;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String login) {
        Claims claims = Jwts.claims().setSubject(login);

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600 * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSigningKey()) 
                .compact();
    }

    public Authentication validateToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String login = claims.getSubject();
        UserDto user = authenticationService.findByLogin(login);

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateCredentials(CredentialsDto credentialsDto) {
        UserDto user = authenticationService.authenticate(credentialsDto);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
    
}
