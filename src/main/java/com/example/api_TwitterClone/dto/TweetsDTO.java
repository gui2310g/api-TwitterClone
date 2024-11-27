package com.example.api_TwitterClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TweetsDTO {
    private final Integer id;
    private final String text;
    private final String banner;
    private final Integer userId;
}
