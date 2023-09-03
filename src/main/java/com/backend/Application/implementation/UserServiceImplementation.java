package com.backend.Application.implementation;

import com.backend.Application.exceptions.BackendException;
import com.backend.Application.model.Users;
import com.backend.Application.repository.UserRepository;
import com.backend.Application.service.JwtService;
import com.backend.Application.service.UserService;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class.getName());

    @Override
    public Users updateUser(String token, Users users) {
        try {
            var user = userRepository.findByUsernameOrEmail(jwtService.extractUsername(token), jwtService.extractUsername(token))
                    .orElseThrow(() -> new BackendException(Response.Status.UNAUTHORIZED.getStatusCode(), "Please login to access this feature."));

            // Update the necessary fields of the existing user
            user.setFirstName(users.getFirstName());
            user.setLastName(users.getLastName());
            user.setEmail(users.getEmail());
            user.setPhoneNumber(users.getPhoneNumber());

            // Save the updated user
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error occurred while updating user: " + e.getMessage());
            throw e;
        }
    }
}