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

    public List<Users> searchUsersByUsername(String username) throws Exception {
        List<Users> users = usersRepository.findByUsernameContainingIgnoreCase(username);

        if(users.isEmpty()) throw new Exception("Could not find users with this username");

        return users;
    }

    public Users updateUser(Users users, Integer id) throws Exception {
        Users updatedUser = usersRepository.findById(id)
                .orElseThrow(() -> new Exception("Could no find this user to update"));

        if(usersRepository.findByEmail(updatedUser.getEmail()).isPresent())
            throw new Exception("This email still exists");

        if(usersRepository.findByUsername(updatedUser.getUsername()).isPresent())
            throw new Exception("This username still exists");

        if(updatedUser.getEmail() != null) updatedUser.setEmail(users.getEmail());
        if(updatedUser.getUsername() != null) updatedUser.setUsername(users.getUsername());
        if(updatedUser.getPassword() != null) updatedUser.setPassword(users.getPassword());

        return usersRepository.save(updatedUser);
    }
}
