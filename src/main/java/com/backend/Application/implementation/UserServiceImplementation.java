package com.backend.Application.implementation;

import com.backend.Application.exceptions.BackendException;
import com.backend.Application.model.Users;
import com.backend.Application.repository.UserRepository;
import com.backend.Application.requests.UpdatePasswordRequest;
import com.backend.Application.requests.UserUpdateRequest;
import com.backend.Application.service.JwtService;
import com.backend.Application.service.UserService;
import com.backend.Application.util.BackendResponse;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class.getName());

    @Override
    public BackendResponse updateUser(String token, UserUpdateRequest userUpdateRequest) {
        try {
            Users user = getUserFromToken(token);

            user.setFirstName(Optional.ofNullable(userUpdateRequest.getFirstName()).orElse(user.getFirstName()));
            user.setLastName(Optional.ofNullable(userUpdateRequest.getLastName()).orElse(user.getLastName()));
            user.setPhoneNumber(Optional.ofNullable(userUpdateRequest.getPhoneNumber()).orElse(user.getPhoneNumber()));
            user.setEmail(Optional.ofNullable(userUpdateRequest.getEmail()).orElse(user.getEmail()));
            user.setUsername(user.getUsername());
            user.setRoles(Optional.ofNullable(userUpdateRequest.getRoles()).orElse(user.getRoles()));

            // Save the updated user
            userRepository.save(user);

            return BackendResponse.builder()
                    .response("User details are updated. To view your details: http://localhost:8080/backendApplication/users/viewUser").build();
        } catch (Exception e) {
            logger.error("Error occurred while updating user: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public BackendResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, String token) {
        Users user = getUserFromToken(token);
        if (passwordEncoder.matches(updatePasswordRequest.getCurrentPassword(), user.getPassword())) {
            if (updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getRetypeNewPassword())) {
                return BackendResponse.builder()
                        .response("Password updated. Updated password will be implemented from next login onwards.").build();
            } else {
                throw new BackendException(Response.Status.EXPECTATION_FAILED.getStatusCode(), "Re-type password should be same as new password, Please provide the same password");
            }
        } else {
            throw new BackendException(Response.Status.EXPECTATION_FAILED.getStatusCode(), "Invalid current password, Please provide the correct current password");
        }
    }

    @Override
    public Users getUserFromToken(String token) {
        return userRepository.findByUsernameOrEmail(jwtService.extractUsername(token), jwtService.extractUsername(token))
                .orElseThrow(() -> new BackendException(Response.Status.UNAUTHORIZED.getStatusCode(), "Please login to access this feature."));
    }
}