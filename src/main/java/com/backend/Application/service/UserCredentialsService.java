package com.backend.Application.service;

import com.backend.Application.model.UserCredentials;

public interface UserCredentialsService {
    UserCredentials login(String username, String password) throws Exception;
    void logout(UserCredentials userCredentials);

    UserCredentials getCustomerByEmail(String email) throws Exception;
}