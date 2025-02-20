package com.example.api_TwitterClone.dto.tweets;

import lombok.Data;

@Data
public class TweetsRequest {
    private Integer id;
    private String text;
    private String banner;
    private Integer userId;
    private String username;
    private String userAvatar;
}
