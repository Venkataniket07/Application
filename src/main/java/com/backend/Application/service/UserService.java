package com.backend.Application.service;

import com.backend.Application.exceptions.BackendException;
import com.backend.Application.model.Users;

public interface UserService {

    Users login(String username, String password) throws BackendException;
    Users getUserByEmail(String email) throws BackendException;
}
