package com.example.api_TwitterClone.controllers;

import com.example.api_TwitterClone.entities.Users;
import com.example.api_TwitterClone.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        Users createdUser = usersService.createUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}

