package com.example.api_TwitterClone.controllers;

import com.example.api_TwitterClone.dto.UserDto;
import com.example.api_TwitterClone.entities.Users;
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
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws Exception {
        UserDto createdUser = usersService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserDto>> findAllUsers() throws Exception {
        List<UserDto> users = usersService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Integer id) throws Exception {
        UserDto user = usersService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Users>> searchUsersByUsername(@RequestParam String username) throws Exception {
        List<Users> users = usersService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserDto userDto,
            Authentication authentication
    ) throws Exception {
        Integer userId = authService.getAuthenticatedUserId(authentication);
        UserDto updatedUser = usersService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }
}

