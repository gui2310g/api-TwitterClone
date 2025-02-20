package com.example.api_TwitterClone.mapper;

import com.example.api_TwitterClone.dto.tweets.TweetsRequest;
import com.example.api_TwitterClone.domain.entities.Tweets;
import com.example.api_TwitterClone.dto.tweets.TweetsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetsMapper {
    Tweets toEntity(TweetsRequest tweetsRequest);

    @Mapping(target = "userId", source = "users.id")
    @Mapping(target = "username", source = "users.username")
    @Mapping(target = "userAvatar", source = "users.avatar")
    TweetsResponse toDto(Tweets tweets);
}
