package com.example.api_TwitterClone.controllers;

import com.example.api_TwitterClone.dto.users.UserRequest;
import com.example.api_TwitterClone.domain.exceptions.UserException;
import com.example.api_TwitterClone.dto.users.UserResponse;
import com.example.api_TwitterClone.security.AuthService;
import com.example.api_TwitterClone.domain.services.UsersService;
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
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.createUser(userRequest));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers()  {
        return ResponseEntity.ok(usersService.findAllUsers());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(usersService.findUserById(id));
    }

    @GetMapping("/findByAuth")
    public ResponseEntity<UserResponse> findUserByuserLogged(Authentication authentication) {
        return ResponseEntity.ok(usersService.findUserLogged(authService.getAuthenticatedUserId(authentication)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsersByUsername(@RequestParam String username) {
        return ResponseEntity.ok(usersService.searchUsersByUsername(username));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest, Authentication auth)  {
        return ResponseEntity.ok(usersService.updateUser(userRequest, authService.getAuthenticatedUserId(auth)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(Authentication authentication) {
        usersService.deleteUser(authService.getAuthenticatedUserId(authentication));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

