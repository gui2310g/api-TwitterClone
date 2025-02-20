package com.example.api_TwitterClone.controllers;

import com.example.api_TwitterClone.dto.TweetsCommentsDTO;
import com.example.api_TwitterClone.dto.tweets.TweetsRequest;
import com.example.api_TwitterClone.domain.exceptions.TweetsException;
import com.example.api_TwitterClone.dto.tweets.TweetsResponse;
import com.example.api_TwitterClone.security.AuthService;
import com.example.api_TwitterClone.domain.services.TweetsService;
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
    public ResponseEntity<TweetsResponse> createTweets(@RequestBody TweetsRequest tweetsRequest, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tweetsService.createTweets(tweetsRequest, authService.getAuthenticatedUserId(auth)));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TweetsResponse>> findAllTweets() {
        return ResponseEntity.ok(tweetsService.findAllTweets());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<TweetsResponse> findTweetsById(@PathVariable Integer id)  {
        return ResponseEntity.ok(tweetsService.findTweetsById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TweetsResponse>> searchTweets(@RequestParam String text)  {
        return ResponseEntity.ok(tweetsService.searchTweets(text));
    }

    @GetMapping("/findByUser/{userId}")
    public ResponseEntity<List<TweetsResponse>> findTweetsByUserId(@PathVariable Integer userId)  {
        return ResponseEntity.ok(tweetsService.findTweetsByUsersId(userId));
    }

    @GetMapping("/findByAuth")
    public ResponseEntity<List<TweetsResponse>> findTweetsByUserLogged(Authentication auth) {
        return ResponseEntity.ok(tweetsService.findTweetsByUserLogged(authService.getAuthenticatedUserId(auth)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TweetsResponse> updateTweets(
            @RequestBody TweetsRequest tweetsRequest,
            @PathVariable Integer id,
            Authentication authentication
    ) throws TweetsException{
        return ResponseEntity.ok(tweetsService.updateTweets(
                tweetsRequest, id, authService.getAuthenticatedUserId(authentication)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTweets(@PathVariable Integer id, Authentication authentication) {
        tweetsService.deleteTweets(id, authService.getAuthenticatedUserId(authentication));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{tweetId}/comments")
    public ResponseEntity<TweetsCommentsDTO> addComments(
            @PathVariable Integer tweetId,
            @RequestBody TweetsCommentsDTO tweetsComments,
            Authentication authentication
    ) {
        return ResponseEntity.ok(tweetsService.addComments(
                tweetId, authService.getAuthenticatedUserId(authentication), tweetsComments));
    }

    @GetMapping("/findAll/{tweetId}/comments")
    public ResponseEntity<List<TweetsCommentsDTO>> findAllCommentsByTweetsId(@PathVariable Integer tweetId) {
        return ResponseEntity.ok(tweetsService.findAllCommentsByTweetsId(tweetId));
    }

    @PutMapping("/update/{commentId}/comments")
    public ResponseEntity<TweetsCommentsDTO> updateComments(
            @PathVariable Integer commentId,
            @RequestBody TweetsCommentsDTO tweetsComments,
            Authentication authentication
    ) {
        return ResponseEntity.ok(tweetsService.updateComments(
                commentId, authService.getAuthenticatedUserId(authentication), tweetsComments));
    }

    @DeleteMapping("/{tweetId}/{commentId}/comments")
    public ResponseEntity<?> deleteComments(
            @PathVariable Integer tweetId,
            @PathVariable Integer commentId,
            Authentication authentication
    ) {
        tweetsService.deleteComments(tweetId, commentId, authService.getAuthenticatedUserId(authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
    