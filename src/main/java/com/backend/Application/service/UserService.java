package com.backend.Application.service;

import com.backend.Application.model.Users;

public interface UserService {

    Users updateUser(String token, Users users);
}
