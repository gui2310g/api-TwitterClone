package com.example.api_TwitterClone.services;

import com.example.api_TwitterClone.entities.Users;
import com.example.api_TwitterClone.config.JwtService;
import com.example.api_TwitterClone.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    private final UsersRepository usersRepository;

    public String login(String email, String password) throws Exception {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Could not find a user with this email"));

        if (!passwordEncoder.matches(password, users.getPassword())) throw new Exception("Invalid password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(users.getUsername(), password);

        return jwtService.generateToken(authentication);
    }

    public Integer getAuthenticatedUserId(Authentication authentication) {
        String username = authentication.getName();
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }
}
