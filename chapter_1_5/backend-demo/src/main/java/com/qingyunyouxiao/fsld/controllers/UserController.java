package com.qingyunyouxiao.fsld.controllers;

import com.qingyunyouxiao.fsld.dtos.UserDto;
import com.qingyunyouxiao.fsld.repository.UserDtoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

// 受保护的用户信息控制器
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDtoRepository userDtoRepository;

    public UserController(UserDtoRepository userDtoRepository) {
        this.userDtoRepository = userDtoRepository;
    }

    // 获取当前用户的资料并查询数据库
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<UserDto> userOptional = userDtoRepository.findByName(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserDto user = userOptional.get();
        return ResponseEntity.ok(Map.of("id", user.getId(), "name", user.getName()));
    }
}
