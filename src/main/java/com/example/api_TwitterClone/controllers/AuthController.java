package com.example.api_TwitterClone.controllers;

import com.example.api_TwitterClone.dto.LoginDto;
import com.example.api_TwitterClone.domain.exceptions.AuthException;
import com.example.api_TwitterClone.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    private ResponseEntity<String> login(@RequestBody LoginDto loginDto) throws AuthException {
        String token = authService.login(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok(token);
    }
}
