package com.example.api_TwitterClone.services;

import com.example.api_TwitterClone.entities.Users;
import com.example.api_TwitterClone.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public Users createUser(Users users) throws Exception {
        if(usersRepository.findByEmail(users.getEmail()).isPresent())
            throw new Exception("This email still exists");

        if(usersRepository.findByUsername(users.getUsername()).isPresent())
            throw new Exception("This username still exists");

        return usersRepository.save(users);
    }

    public List<Users> findAllUsers() throws Exception {
        List<Users> users = usersRepository.findAll();

        if (users.isEmpty()) throw new Exception("No users found");

        return users;
    }

    public Users findUserById(Integer id) throws Exception {
        return usersRepository.findById(id).orElseThrow(
                () -> new Exception("Could not find user with this id"));
    }
}
