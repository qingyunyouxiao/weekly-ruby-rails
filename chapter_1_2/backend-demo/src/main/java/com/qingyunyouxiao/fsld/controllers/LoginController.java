package com.qingyunyouxiao.fsld.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        boolean success = "2293".equals(username) && "2293".equals(password);

        Map<String, Object> response = new HashMap<>();
        response.put("status", success ? "success" : "fail");
        response.put("message", success ? "Login successful" : "Invalid credentials");
        return response;
    }
}
