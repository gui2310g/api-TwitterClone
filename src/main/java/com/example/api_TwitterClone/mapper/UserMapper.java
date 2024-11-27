package com.example.api_TwitterClone.mapper;

import com.example.api_TwitterClone.dto.UserDto;
import com.example.api_TwitterClone.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toEntity(UserDto userDto);

    @Mapping(target = "id", source = "id")
    UserDto toDto(Users user);
}