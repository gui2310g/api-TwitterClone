package com.example.api_TwitterClone.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TweetsCommentsDTO {
    private Integer id;
    private String text;
    private Integer userId;
    private Integer tweetId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;
}
