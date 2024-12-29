package com.example.api_TwitterClone.services;

import com.example.api_TwitterClone.dto.TweetsCommentsDTO;
import com.example.api_TwitterClone.dto.TweetsDto;
import com.example.api_TwitterClone.entities.Tweets;
import com.example.api_TwitterClone.entities.TweetsComments;
import com.example.api_TwitterClone.entities.Users;
import com.example.api_TwitterClone.exceptions.TweetsException;
import com.example.api_TwitterClone.mapper.TweetsCommentsMapper;
import com.example.api_TwitterClone.mapper.TweetsMapper;
import com.example.api_TwitterClone.repositories.TweetsCommentsRepository;
import com.example.api_TwitterClone.repositories.TweetsRepository;
import com.example.api_TwitterClone.repositories.UsersRepository;
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

    public TweetsDto createTweets(TweetsDto tweetsDTO, Integer userId) throws TweetsException {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new TweetsException("Cannot find a user to create a tweet"));

        Tweets tweets = tweetsMapper.toEntity(tweetsDTO);
        tweets.setUsers(users);
        Tweets savedTweet = tweetsRepository.save(tweets);

        return tweetsMapper.toDto(savedTweet);
    }

    public List<TweetsDto> findAllTweets() throws TweetsException {
        List<Tweets> tweets = tweetsRepository.findAll();

        if (tweets.isEmpty()) throw new TweetsException("No tweets found");

        return tweets.stream().map(tweetsMapper::toDto).toList();
    }

    public TweetsDto findTweetsById(Integer id) throws TweetsException {
       Tweets tweets  = tweetsRepository.findById(id)
               .orElseThrow(() -> new TweetsException("Could not find a tweet with this id"));

       return tweetsMapper.toDto(tweets);
    }

    public List<TweetsDto> searchTweets(String text) throws TweetsException {
        List<Tweets> tweets = tweetsRepository.findByTextContainingIgnoreCase(text);

        if (tweets.isEmpty()) throw new TweetsException("Could not find tweets with this text");

        return tweets.stream().map(tweetsMapper::toDto).toList();
    }

    public List<TweetsDto> findTweetsByUsersId(Integer userId) throws TweetsException {
        List<Tweets> tweets = tweetsRepository.findByUsersId(userId);

        if (tweets.isEmpty()) throw new TweetsException("No tweets found for this user");

        return tweets.stream().map(tweetsMapper::toDto).toList();
    }

    public List<TweetsDto> findTweetsByUserLogged(Integer userId) throws TweetsException {
        List<Tweets> tweets = tweetsRepository.findByUsersId(userId);

        if (tweets.isEmpty()) throw new TweetsException("No tweets found for this user");

        return tweets.stream().map(tweetsMapper::toDto).toList();
    }

    public TweetsDto updateTweets(TweetsDto tweetsDto, Integer id, Integer userId) throws TweetsException{
        Tweets tweets = tweetsRepository.findById(id)
                .orElseThrow(() -> new TweetsException("Can't find a tweet with this id"));

        if (!tweets.getUsers().getId().equals(userId)) throw new TweetsException("You can only update your own tweets");
        if (tweetsDto.getBanner() != null) tweets.setBanner(tweetsDto.getBanner());
        if (tweetsDto.getText() != null) tweets.setText(tweetsDto.getText());

        Tweets updatedTweet = tweetsRepository.save(tweets);

        return tweetsMapper.toDto(updatedTweet);
    }

    public TweetsDto deleteTweets(TweetsDto tweetsDto, Integer id, Integer userId) throws TweetsException {
        Tweets tweets = tweetsRepository.findById(id)
                .orElseThrow(() -> new TweetsException("Can't find a tweet with this id"));

        if(!tweets.getUsers().getId().equals(userId)) throw new TweetsException("You can only delete your own tweets");

       tweetsRepository.delete(tweets);

       return tweetsMapper.toDto(tweets);
    }

    public TweetsCommentsDTO addComments(
            Integer tweetsId,
            Integer userId,
            TweetsCommentsDTO tweetsCommentsDTO
    ) throws TweetsException {
        Tweets tweets = tweetsRepository.findById(tweetsId)
               .orElseThrow(() -> new TweetsException("Can't find a tweet with this id"));

        Users users = usersRepository.findById(userId)
               .orElseThrow(() -> new TweetsException("Cannot find a user to add a comment"));

        TweetsComments tweetsComments = tweetsCommentsMapper.toEntity(tweetsCommentsDTO);
        tweetsComments.setTweet(tweets);
        tweetsComments.setUsers(users);
        TweetsComments savedComments = tweetsCommentsRepository.save(tweetsComments);

        return tweetsCommentsMapper.toDto(savedComments);
    }

    public List<TweetsCommentsDTO> findAllCommentsByTweetsId(Integer tweetsId) throws TweetsException {
        List<TweetsComments> tweetsComments = tweetsCommentsRepository.findByTweetId(tweetsId);

        if (tweetsComments.isEmpty()) throw new TweetsException("No comments found for this tweet");

        return tweetsComments.stream().map(tweetsCommentsMapper::toDto).toList();
    }

    public TweetsCommentsDTO updateComments(
            Integer commentId,
            Integer userId,
            TweetsCommentsDTO tweetsCommentsDTO
    ) throws TweetsException {
        TweetsComments tweetsComments = tweetsCommentsRepository.findById(commentId)
                   .orElseThrow(() -> new TweetsException("Can't find a comment with this id"));

        if (!tweetsComments.getUsers().getId().equals(userId))
            throw new TweetsException("You can only update your own comments");

        if (tweetsCommentsDTO.getText() != null) tweetsComments.setText(tweetsCommentsDTO.getText());
        if (tweetsCommentsDTO.getBanner() != null) tweetsComments.setBanner(tweetsCommentsDTO.getBanner());

        TweetsComments updatedComment = tweetsCommentsRepository.save(tweetsComments);

        return tweetsCommentsMapper.toDto(updatedComment);
    }

    public TweetsCommentsDTO deleteComments(Integer tweetsId, Integer commentId, Integer userId) throws TweetsException {
        Tweets tweets = tweetsRepository.findById(tweetsId)
                .orElseThrow(() -> new TweetsException("Can't find a tweet with this id"));

        TweetsComments tweetsComments = tweetsCommentsRepository.findById(commentId)
                .orElseThrow(() -> new TweetsException("Can't find a comment with this id"));

        if (!tweetsComments.getUsers().getId().equals(userId))
            throw new TweetsException("You can only delete your own comments");

        tweetsCommentsRepository.delete(tweetsComments);

        return tweetsCommentsMapper.toDto(tweetsComments);
    }
}
