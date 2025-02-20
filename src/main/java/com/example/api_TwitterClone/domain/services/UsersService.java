package com.example.api_TwitterClone.domain.services;

import com.example.api_TwitterClone.dto.users.UserRequest;
import com.example.api_TwitterClone.domain.entities.Users;
import com.example.api_TwitterClone.domain.exceptions.UserException;
import com.example.api_TwitterClone.dto.users.UserResponse;
import com.example.api_TwitterClone.mapper.UserMapper;
import com.example.api_TwitterClone.domain.repositories.UsersRepository;
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

    public UserResponse createUser(UserRequest dto) {
        if (usersRepository.findByEmail(dto.getEmail()).isPresent()) throw new UserException("This email still exists");

        if (usersRepository.findByUsername(dto.getUsername()).isPresent())
            throw new UserException("This username still exists");

        Users user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return userMapper.toDto(usersRepository.save(user));
    }

    public List<UserResponse> findAllUsers() throws UserException {
        return  usersRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    public UserResponse findUserById(Integer id) {
        return usersRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new UserException("Could not find user with this id"));
    }

    public UserResponse findUserLogged(Integer id) {
        return usersRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new UserException("Could not find user with this id"));
    }

    public List<UserResponse> searchUsersByUsername(String username) {
        return usersRepository.findByUsernameContainingIgnoreCase(username).stream().map(this::toDto).toList();
    }

    public UserResponse updateUser(UserRequest userRequest, Integer id)  {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UserException("Could not find this user to update"));

        if (usersRepository.findByEmail(userRequest.getEmail()).isPresent()
                && !user.getEmail().equals(userRequest.getEmail()))
            throw new UserException("This email is already in use by another user");

        if (userRequest.getUsername() != null) throw new UserException("You can't update the username");

        if (userRequest.getPassword() != null && passwordEncoder.matches(userRequest.getPassword(), user.getPassword()))
            throw new UserException("You are already using this password");

        if (userRequest.getEmail() != null) user.setEmail(userRequest.getEmail());
        if (userRequest.getPassword() != null) user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        if (userRequest.getAvatar() != null) user.setAvatar(userRequest.getAvatar());
        if (userRequest.getBackground() != null) user.setBackground(userRequest.getBackground());

        return userMapper.toDto(usersRepository.save(user));
    }

    public void deleteUser(Integer id) {
        findUserById(id);
        usersRepository.deleteById(id);
    }

    public UserResponse toDto(Users users) { return userMapper.toDto(users); }
}

