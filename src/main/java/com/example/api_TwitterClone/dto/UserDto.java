package com.example.api_TwitterClone.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer Id;
    private String username;
    private String email;
    private String password;
    private String name;
    private String avatar;
    private String background;
}
