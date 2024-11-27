package com.example.api_TwitterClone.services;

import com.example.api_TwitterClone.dto.TweetsDTO;
import com.example.api_TwitterClone.entities.Tweets;
import com.example.api_TwitterClone.entities.Users;
import com.example.api_TwitterClone.mapper.TweetsMapper;
import com.example.api_TwitterClone.repositories.TweetsRepository;
import com.example.api_TwitterClone.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TweetsService {

    private final TweetsRepository tweetsRepository;

    private final UsersRepository usersRepository;

    private final TweetsMapper tweetsMapper;

    public TweetsDTO createTweets(TweetsDTO tweetsDTO, Integer userId) throws Exception {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new Exception("Cannot find a user to create a tweet"));

        Tweets tweets = tweetsMapper.toEntity(tweetsDTO);
        tweets.setUsers(users);
        Tweets savedTweet = tweetsRepository.save(tweets);

        return tweetsMapper.toDto(savedTweet);
    }
}
