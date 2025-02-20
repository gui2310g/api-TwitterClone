package com.example.api_TwitterClone.dto.tweets;

import com.example.api_TwitterClone.domain.entities.TweetsComments;
import lombok.Data;

import java.util.Set;

@Data
public class TweetsResponse {
    private Integer id;
    private String text;
    private String banner;
    private Integer userId;
    private String username;
    private String userAvatar;
    private Set<TweetsComments> comments;
}
