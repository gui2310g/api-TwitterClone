package com.example.api_TwitterClone.controllers;

import com.example.api_TwitterClone.dto.TweetsDto;
import com.example.api_TwitterClone.entities.Tweets;
import com.example.api_TwitterClone.services.AuthService;
import com.example.api_TwitterClone.services.TweetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetsController {

    @Autowired
    private TweetsService tweetsService;

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<TweetsDto> createTweets(
            @RequestBody TweetsDto tweetsDTO,
            Authentication authentication
    ) throws Exception {
        Integer userId = authService.getAuthenticatedUserId(authentication);
        TweetsDto createdTweet = tweetsService.createTweets(tweetsDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTweet);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TweetsDto>> findAllTweets() throws Exception {
        List<TweetsDto> tweets = tweetsService.findAllTweets();
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<TweetsDto> findTweetSById(@PathVariable Integer id) throws Exception {
        TweetsDto tweet = tweetsService.findTweetsById(id);
        return ResponseEntity.ok(tweet);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TweetsDto>> searchTweets(@RequestParam String text) throws Exception {
        List<TweetsDto> tweets = tweetsService.searchTweets(text);
        return ResponseEntity.ok(tweets);
    }
}
