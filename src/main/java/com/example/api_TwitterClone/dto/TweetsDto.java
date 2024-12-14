package com.example.api_TwitterClone.dto;

import lombok.Data;

@Data
public class TweetsDto {
    private Integer id;
    private String text;
    private String banner;
    private Integer userId;
    private String username;
    private String userAvatar;
}
