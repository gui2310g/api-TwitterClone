package com.example.api_TwitterClone.security;

import com.example.api_TwitterClone.domain.entities.Users;
import com.example.api_TwitterClone.domain.exceptions.AuthException;
import com.example.api_TwitterClone.domain.repositories.UsersRepository;
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

    public String login(String email, String password) throws AuthException {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("Could not find a user with this email"));

        if (!passwordEncoder.matches(password, users.getPassword())) throw new AuthException("Invalid password");

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
