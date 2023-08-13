package com.backend.Application.exceptions;

import com.backend.Application.model.UserCredentials;
import com.backend.Application.repository.UserCredentialsRepository;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserCredentialsValidations extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(UserCredentialsValidations.class);
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    public void userCredentialsValidations(UserCredentials userCredentials) throws BackendException {
        emailValidation(userCredentials);
        emailAlreadyExists(userCredentials);
        validatePassword(userCredentials.getPassword());
        logger.info("User-credentials are valid.");
    }

    private void emailAlreadyExists(UserCredentials userCredentials) {
        UserCredentials existingUserCredentialsByEmail = userCredentialsRepository.findByEmail(userCredentials.getEmail());

        if (existingUserCredentialsByEmail != null) {
            logger.error("email-id already exists!!");
            throw new BackendException(Response.Status.CONFLICT.getStatusCode(), "email already exists!! User another email-id or login with the registered email/username.");
        }
        logger.info("email-id is registered.");
    }

    private void emailValidation(UserCredentials userCredentials) {
        // Email-id can't be null and should contain '@' & '.com'
        if (userCredentials.getEmail().endsWith(".com") && userCredentials.getEmail().contains("@")) {
            logger.info("email-id is valid.");
        } else {
            logger.error("invalid email-id.");
            throw new BackendException(Response.Status.EXPECTATION_FAILED.getStatusCode(), "Please enter a valid email-id. email-id should contain '@' and '.com'");
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            logger.error("Password cannot be null");
            throw new BackendException(Response.Status.FORBIDDEN.getStatusCode(), "Password cannot be empty.");
        }

        // Minimum length of 8 characters
        if (password.length() < 8) {
            logger.error("Password length should be at least 8 characters.");
            throw new BackendException(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Password length should be at least 8 characters.");
        }

        // At least one uppercase letter
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            logger.error("Password should contain at least one uppercase letter.");
            throw new BackendException(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Password should contain at least one uppercase letter.");
        }

        // At least one lowercase letter
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            logger.error("Password should contain at least one lowercase letter.");
            throw new BackendException(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Password should contain at least one lowercase letter.");
        }

        // At least one numeric digit
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            logger.error("Password should contain at least one numeric digit.");
            throw new BackendException(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Password should contain at least one numeric digit.");
        }

        // At least one special character
        if (!Pattern.compile("[!@#$%^&*()-+=]").matcher(password).find()) {
            logger.error("Password should contain at least one special character.");
            throw new BackendException(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Password should contain at least one special character.");
        }

        logger.info("Password is valid.");
    }
}