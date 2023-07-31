package com.backend.Application.repository;

import com.backend.Application.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer> {
    UserCredentials findByUsername(String username);

    UserCredentials findByEmail(String email);
}