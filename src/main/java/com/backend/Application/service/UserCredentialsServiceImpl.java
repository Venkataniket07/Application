package com.backend.Application.service;

import com.backend.Application.model.UserCredentials;
import com.backend.Application.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService {
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserCredentials login(String usernameOrEmail, String password) throws Exception {
        UserCredentials userCredentials = getUserCredentialsWithUsernameOrEmail(usernameOrEmail);
        if (userCredentials == null) {
            throw new Exception("The username/email-id: " + usernameOrEmail + " doesn't exists in our database");
        }
        if (userCredentials.getPassword().equals(password)) {
            return userCredentials;
        } else
            throw new Exception("Incorrect Password: " + password);
    }

    @Override
    public void logout(UserCredentials userCredentials) {
        // Implement logout logic here
    }

    @Override
    public UserCredentials getCustomerByEmail(String email) throws Exception {
        UserCredentials userCredentials = userCredentialsRepository.findByEmail(email);

        if (userCredentials == null) {
            throw new Exception("Email: " + email + " is not registered");
        } else
            return userCredentials;
    }

    private UserCredentials getUserCredentialsWithUsernameOrEmail(String usernameOrEmail) {
        UserCredentials userCredentials = userCredentialsRepository.findByUsername(usernameOrEmail);
        if (userCredentials != null) {
            return userCredentials;
        }

        userCredentials = userCredentialsRepository.findByEmail(usernameOrEmail);
        return userCredentials;
    }
}