package com.example.api_TwitterClone.repositories;

import com.example.api_TwitterClone.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByEmail(String email);

    List<Users> searchUsersByUsername(String username);

}
