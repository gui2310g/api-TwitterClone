package com.example.api_TwitterClone.mapper;

import com.example.api_TwitterClone.dto.TweetsDto;
import com.example.api_TwitterClone.entities.Tweets;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetsMapper {
    Tweets toEntity(TweetsDto tweetsDTO);

    @Mapping(target = "userId", source = "users.id")
    TweetsDto toDto(Tweets tweets);
}