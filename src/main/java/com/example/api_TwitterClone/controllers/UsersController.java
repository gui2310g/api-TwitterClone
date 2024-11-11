package com.example.api_TwitterClone.controllers;

import com.example.api_TwitterClone.entities.Users;
import com.example.api_TwitterClone.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users users) throws Exception {
        Users createdUser = usersService.createUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Users>> findAllUsers() throws Exception {
        List<Users> users = usersService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Users> findUserById(@PathVariable Integer id) throws Exception {
        Users user = usersService.findUserById(id);
        return ResponseEntity.ok(user);
    }
}

