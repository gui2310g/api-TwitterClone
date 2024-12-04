package com.example.api_TwitterClone.services;

import com.example.api_TwitterClone.dto.UserDto;
import com.example.api_TwitterClone.entities.Users;
import com.example.api_TwitterClone.mapper.UserMapper;
import com.example.api_TwitterClone.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserDto createUser(UserDto userDto) throws Exception {
        if (usersRepository.findByEmail(userDto.getEmail()).isPresent()) throw new Exception("This email still exists");

        if (usersRepository.findByUsername(userDto.getUsername()).isPresent())
            throw new Exception("This username still exists");

        Users user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users savedUser = usersRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    public List<UserDto> findAllUsers() throws Exception {
        List<Users> users = usersRepository.findAll();

        if (users.isEmpty()) throw new Exception("No users found");

        return users.stream().map(userMapper::toDto).toList();
    }

    public UserDto findUserById(Integer id) throws Exception {
        Users user = usersRepository.findById(id).orElseThrow(() -> new Exception("Could not find user with this id"));

        return userMapper.toDto(user);
    }

    public List<UserDto> searchUsersByUsername(String username) throws Exception {
        List<Users> users = usersRepository.findByUsernameContainingIgnoreCase(username);

        if (users.isEmpty()) throw new Exception("Could not find users with this username");

        return users.stream().map(userMapper::toDto).toList();
    }

    public UserDto updateUser(UserDto userDto, Integer id) throws Exception {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new Exception("Could not find this user to update"));

        if (usersRepository.findByEmail(userDto.getEmail()).isPresent() && !user.getEmail().equals(userDto.getEmail()))
            throw new Exception("This email is already in use by another user");

        if (userDto.getUsername() != null) throw new Exception("You can't update the username");

        if (userDto.getPassword() != null && passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
            throw new Exception("You are already using this password");

        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getAvatar() != null) user.setAvatar(userDto.getAvatar());
        if (userDto.getBackground() != null) user.setBackground(userDto.getBackground());

        Users updatedUser = usersRepository.save(user);

        return userMapper.toDto(updatedUser);
    }
}
