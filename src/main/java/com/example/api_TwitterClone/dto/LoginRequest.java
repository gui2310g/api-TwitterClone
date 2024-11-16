package com.example.api_TwitterClone.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private final String email;
    private final String password;
}
