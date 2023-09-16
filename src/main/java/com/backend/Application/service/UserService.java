package com.backend.Application.service;

import com.backend.Application.model.Users;
import com.backend.Application.requests.UpdatePasswordRequest;
import com.backend.Application.requests.UserUpdateRequest;
import com.backend.Application.util.BackendResponse;

public interface UserService {

    BackendResponse updateUser(String token, UserUpdateRequest userUpdateRequest);

    BackendResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, String token);

    Users getUserFromToken(String token);
}
