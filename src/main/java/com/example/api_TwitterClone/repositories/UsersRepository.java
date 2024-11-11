package com.example.api_TwitterClone.repositories;

import com.example.api_TwitterClone.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

    List<Users> searchUsersByUsername(String username);

    Optional<Users> findByUsername(String username);

}
