package com.example.api_TwitterClone.domain.repositories;

import com.example.api_TwitterClone.domain.entities.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetsRepository extends JpaRepository<Tweets, Integer> {
    List<Tweets> findByUsersId(Integer userId);

    List<Tweets> findByTextContainingIgnoreCase(String text);
}
