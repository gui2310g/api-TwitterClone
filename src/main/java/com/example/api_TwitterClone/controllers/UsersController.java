package com.example.api_TwitterClone.controllers;

import com.example.api_TwitterClone.dto.UserDto;
import com.example.api_TwitterClone.entities.Users;
import com.example.api_TwitterClone.exceptions.UserException;
import com.example.api_TwitterClone.services.AuthService;
import com.example.api_TwitterClone.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws UserException {
        UserDto createdUser = usersService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserDto>> findAllUsers() throws UserException {
        List<UserDto> users = usersService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Integer id) throws UserException {
        UserDto user = usersService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findByAuth")
    public ResponseEntity<UserDto> findUserByAuth(Authentication authentication) throws UserException {
        Integer userId = authService.getAuthenticatedUserId(authentication);
        UserDto user = usersService.findUserByAuth(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsersByUsername(@RequestParam String username) throws UserException {
        List<UserDto> users = usersService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserDto userDto,
            Authentication authentication
    ) throws UserException {
        Integer userId = authService.getAuthenticatedUserId(authentication);
        UserDto updatedUser = usersService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<UserDto> deleteUser(UserDto userDto, Authentication authentication) throws UserException {
        Integer userId = authService.getAuthenticatedUserId(authentication);
        UserDto deletedUser = usersService.deleteUser(userDto, userId);
        return ResponseEntity.ok(deletedUser);
    }
}

