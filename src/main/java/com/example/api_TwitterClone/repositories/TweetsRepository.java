package com.example.api_TwitterClone.repositories;

import com.example.api_TwitterClone.entities.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetsRepository extends JpaRepository<Tweets, Integer> {
    List<Tweets> findByUsersId(Integer userId);

    Optional<Tweets> searchTweetsByText(String text);
}
