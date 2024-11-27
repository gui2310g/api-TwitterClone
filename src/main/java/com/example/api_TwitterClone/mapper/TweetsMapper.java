package com.example.api_TwitterClone.mapper;

import com.example.api_TwitterClone.dto.TweetsDTO;
import com.example.api_TwitterClone.entities.Tweets;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetsMapper {
    Tweets toEntity(TweetsDTO tweetsDTO);

    @Mapping(target = "userId", source = "users.id")
    TweetsDTO toDto(Tweets tweets);
}
