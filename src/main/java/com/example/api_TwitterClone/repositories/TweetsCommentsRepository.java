package com.example.api_TwitterClone.repositories;

import com.example.api_TwitterClone.entities.TweetsComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetsCommentsRepository extends JpaRepository<TweetsComments, Integer> {
    List<TweetsComments> findByTweetId(Integer tweetId);
}
