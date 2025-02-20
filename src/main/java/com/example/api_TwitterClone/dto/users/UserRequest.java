package com.example.api_TwitterClone.dto.users;

import lombok.Data;

@Data
public class UserRequest {
    private Integer Id;
    private String username;
    private String email;
    private String password;
    private String name;
    private String avatar;
    private String background;
}
