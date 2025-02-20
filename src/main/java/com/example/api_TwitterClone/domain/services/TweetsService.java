package com.example.api_TwitterClone.domain.services;

import com.example.api_TwitterClone.dto.TweetsCommentsDTO;
import com.example.api_TwitterClone.dto.tweets.TweetsRequest;
import com.example.api_TwitterClone.domain.entities.Tweets;
import com.example.api_TwitterClone.domain.entities.TweetsComments;
import com.example.api_TwitterClone.domain.entities.Users;
import com.example.api_TwitterClone.domain.exceptions.TweetsException;
import com.example.api_TwitterClone.dto.tweets.TweetsResponse;
import com.example.api_TwitterClone.mapper.TweetsCommentsMapper;
import com.example.api_TwitterClone.mapper.TweetsMapper;
import com.example.api_TwitterClone.domain.repositories.TweetsCommentsRepository;
import com.example.api_TwitterClone.domain.repositories.TweetsRepository;
import com.example.api_TwitterClone.domain.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TweetsService {

    private final TweetsRepository tweetsRepository;

    private final TweetsCommentsRepository tweetsCommentsRepository;

    private final TweetsCommentsMapper tweetsCommentsMapper;

    private final UsersRepository usersRepository;

    private final TweetsMapper tweetsMapper;

    public TweetsResponse createTweets(TweetsRequest tweetsRequest, Integer userId)  {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new TweetsException("Cannot find a user to create a tweet"));

        Tweets tweets = tweetsMapper.toEntity(tweetsRequest);
        tweets.setUsers(users);

        return tweetsMapper.toDto(tweetsRepository.save(tweets));
    }

    public List<TweetsResponse> findAllTweets() throws TweetsException {
        return tweetsRepository.findAll().stream().map(tweetsMapper::toDto).toList();
    }

    public TweetsResponse findTweetsById(Integer id) throws TweetsException {
       return tweetsRepository.findById(id).map(tweetsMapper::toDto)
               .orElseThrow(() -> new TweetsException("Could not find a tweet with this id"));
    }

    public List<TweetsResponse> searchTweets(String text) throws TweetsException {
        return tweetsRepository.findByTextContainingIgnoreCase(text).stream().map(tweetsMapper::toDto).toList();
    }

    public List<TweetsResponse> findTweetsByUsersId(Integer userId) {
        return tweetsRepository.findByUsersId(userId).stream().map(tweetsMapper::toDto).toList();
    }

    public List<TweetsResponse> findTweetsByUserLogged(Integer userId) {
        return tweetsRepository.findByUsersId(userId).stream().map(tweetsMapper::toDto).toList();
    }

    public TweetsResponse updateTweets(TweetsRequest tweetsRequest, Integer id, Integer userId) {
        Tweets tweets = tweetsRepository.findById(id).orElseThrow(() -> new TweetsException("tweet id not found"));

        if (!tweets.getUsers().getId().equals(userId)) throw new TweetsException("You can only update your own tweets");
        if (tweetsRequest.getBanner() != null) tweets.setBanner(tweetsRequest.getBanner());
        if (tweetsRequest.getText() != null) tweets.setText(tweetsRequest.getText());

        return tweetsMapper.toDto(tweetsRepository.save(tweets));
    }

    public void deleteTweets(Integer id, Integer userId) {
        Tweets tweets = tweetsRepository.findById(id).orElseThrow(() -> new TweetsException("tweet id not found"));

        if(!tweets.getUsers().getId().equals(userId)) throw new TweetsException("You can only delete your own tweets");

       tweetsRepository.delete(tweets);
    }

    public TweetsCommentsDTO addComments(Integer tweetsId, Integer userId, TweetsCommentsDTO tweetsCommentsDTO) {
        Tweets tweet = tweetsRepository.findById(tweetsId).orElseThrow(() -> new TweetsException("tweet id not found"));

        Users users = usersRepository.findById(userId).orElseThrow(() -> new TweetsException("user not found"));

        TweetsComments tweetsComments = tweetsCommentsMapper.toEntity(tweetsCommentsDTO);
        tweetsComments.setTweet(tweet);
        tweetsComments.setUsers(users);

        return tweetsCommentsMapper.toDto(tweetsCommentsRepository.save(tweetsComments));
    }

    public List<TweetsCommentsDTO> findAllCommentsByTweetsId(Integer tweetsId) {
        return tweetsCommentsRepository.findByTweetId(tweetsId).stream().map(tweetsCommentsMapper::toDto).toList();
    }

    public TweetsCommentsDTO updateComments(Integer commentId, Integer userId, TweetsCommentsDTO tweetsCommentsDTO) {
        TweetsComments tweetsComments = tweetsCommentsRepository.findById(commentId)
                   .orElseThrow(() -> new TweetsException("Can't find a comment with this id"));

        if (!tweetsComments.getUsers().getId().equals(userId))
            throw new TweetsException("You can only update your own comments");

        if (tweetsCommentsDTO.getText() != null) tweetsComments.setText(tweetsCommentsDTO.getText());
        if (tweetsCommentsDTO.getBanner() != null) tweetsComments.setBanner(tweetsCommentsDTO.getBanner());

        return tweetsCommentsMapper.toDto(tweetsCommentsRepository.save(tweetsComments));
    }

    public void deleteComments(Integer commentId, Integer userId)  {
        TweetsComments tweetsComments = tweetsCommentsRepository.findById(commentId)
                .orElseThrow(() -> new TweetsException("Can't find a comment with this id"));

        if (!tweetsComments.getUsers().getId().equals(userId))
            throw new TweetsException("You can only delete your own comments");

        tweetsCommentsRepository.delete(tweetsComments);
    }
}
