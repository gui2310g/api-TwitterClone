package com.example.api_TwitterClone.controllers;

import com.example.api_TwitterClone.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;
}
