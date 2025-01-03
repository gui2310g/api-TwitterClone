package com.example.api_TwitterClone.mapper;

import com.example.api_TwitterClone.dto.TweetsCommentsDTO;
import com.example.api_TwitterClone.entities.TweetsComments;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetsCommentsMapper {
    TweetsComments toEntity(TweetsCommentsDTO tweetsCommentsDTO);

    @Mapping(target = "userId", source = "users.id")
    @Mapping(target = "tweetId", source = "tweet.id")
    @Mapping(target = "username", source = "users.username")
    @Mapping(target = "userAvatar", source = "users.avatar")
    TweetsCommentsDTO toDto(TweetsComments tweetsComments);
}

