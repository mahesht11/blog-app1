package com.blog.app.controller;

import com.blog.app.dto.LoginDto;
import com.blog.app.dto.RegisterDto;
import com.blog.app.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;


    @PostMapping(value={"/login", "/signin"})
    public ResponseEntity<String> login(LoginDto loginDto){
        String response = authService.login(loginDto);
        return  ResponseEntity.ok(response);
    }

    @PostMapping(value={"/register", "/signup"})
    public ResponseEntity<String> register(RegisterDto registerDto){
        String response = authService.register(registerDto);
        return ResponseEntity.ok(response);
    }
}
