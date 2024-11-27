package com.example.api_TwitterClone.controllers;

import com.example.api_TwitterClone.dto.TweetsDto;
import com.example.api_TwitterClone.services.AuthService;
import com.example.api_TwitterClone.services.TweetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tweets")
public class TweetsController {

    @Autowired
    private TweetsService tweetsService;

    @Autowired
    private AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<TweetsDto> createTweets(
            @RequestBody TweetsDto tweetsDTO,
            Authentication authentication
    ) throws Exception {
        Integer userId = authService.getAuthenticatedUserId(authentication);
        TweetsDto createdTweet = tweetsService.createTweets(tweetsDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTweet);
    }
}
