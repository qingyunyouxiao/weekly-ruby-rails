package com.qingyunyouxiao.fsld.controllers;

import com.qingyunyouxiao.fsld.dtos.UserDto;
import com.qingyunyouxiao.fsld.jwt.JwtUtil;
import com.qingyunyouxiao.fsld.repository.UserDtoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

// 用户注册和登录的控制器
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDtoRepository userDtoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserDtoRepository userDtoRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userDtoRepository = userDtoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    //  接受 username 和 password
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        Optional<UserDto> existingUser = userDtoRepository.findByName(username);
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        UserDto user = new UserDto();
        user.setName(username);
        user.setPassword(passwordEncoder.encode(password));
        userDtoRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        Optional<UserDto> userOptional = userDtoRepository.findByName(username);
        if (userOptional.isEmpty() || !passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

        // 生成 JWT token 并返回
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
