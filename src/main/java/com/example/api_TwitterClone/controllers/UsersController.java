package com.example.api_TwitterClone.controllers;

import com.example.api_TwitterClone.dto.UserDto;
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
    public ResponseEntity<Users> createUser(@RequestBody UserDto userDto) throws Exception {
        Users createdUser = usersService.createUser(userDto);
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

    @GetMapping("/search")
    public ResponseEntity<List<Users>> searchUsersByUsername(@RequestParam String username) throws Exception {
        List<Users> users = usersService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }
}

