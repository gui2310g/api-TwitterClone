package com.example.api_TwitterClone.services;

import com.example.api_TwitterClone.dto.UserDto;
import com.example.api_TwitterClone.entities.Users;
import com.example.api_TwitterClone.mapper.UserMapper;
import com.example.api_TwitterClone.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public Users createUser(UserDto userDto) throws Exception {
        if(usersRepository.findByEmail(userDto.getEmail()).isPresent())
            throw new Exception("This email still exists");

        if(usersRepository.findByUsername(userDto.getUsername()).isPresent())
            throw new Exception("This username still exists");

        Users user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return usersRepository.save(user);
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

        if (usersRepository.findByEmail(users.getEmail()).isPresent()
                && !updatedUser.getEmail().equals(users.getEmail())) {
            throw new Exception("This email is already in use by another user");
        }

        if (usersRepository.findByUsername(users.getUsername()).isPresent()
                && !updatedUser.getUsername().equals(users.getUsername())) {
            throw new Exception("This username is already in use by another user");
        }

        if (users.getPassword() != null
                && passwordEncoder.matches(users.getPassword(), updatedUser.getPassword())) {
            throw new Exception("You are already using this password");
        }

        if(updatedUser.getEmail() != null) updatedUser.setEmail(users.getEmail());
        if(updatedUser.getUsername() != null) updatedUser.setUsername(users.getUsername());
        if(updatedUser.getPassword() != null) updatedUser.setPassword(users.getPassword());

        return usersRepository.save(updatedUser);
    }
}
