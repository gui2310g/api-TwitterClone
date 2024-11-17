package com.example.api_TwitterClone.dto;

import lombok.Data;

@Data
public class UserDto {
    private final String username;
    private final String email;
    private final String password;
    private final String name;
    private final String avatar;
    private final String background;
}
