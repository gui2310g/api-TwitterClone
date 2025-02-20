package com.example.api_TwitterClone.mapper;

import com.example.api_TwitterClone.dto.users.UserRequest;
import com.example.api_TwitterClone.domain.entities.Users;
import com.example.api_TwitterClone.dto.users.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toEntity(UserRequest userRequest);

    @Mapping(target = "id", source = "id")
    UserResponse toDto(Users user);
}