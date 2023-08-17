package com.backend.Application.repository;

import com.backend.Application.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query(value = "SELECT u FROM Users u WHERE u.username = ?1 OR u.email = ?2")
    Optional<Users> findByUsernameOrEmail(String username, String email);

    Optional<Users> findByEmail(String email);
}