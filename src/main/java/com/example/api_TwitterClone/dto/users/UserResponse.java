package com.example.api_TwitterClone.dto.users;

import com.example.api_TwitterClone.domain.entities.Tweets;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {
    private Integer Id;
    private String username;
    private String email;
    private String password;
    private String name;
    private String avatar;
    private String background;
    private Set<Tweets> tweets;
    private Set<Tweets> comments;
}
