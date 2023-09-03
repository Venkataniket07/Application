package com.backend.Application.service;

import com.backend.Application.model.Users;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * TokenManagementService is used for storing user generated token with-in the application itself.
 * Since tokens contains user credentials they should not be stored in database.
 *
 * @returns Hash-mapping between user and the token which will be used for user authentication.
 */
@Service
public class TokenManagementService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TokenManagementService.class);
    /**
     * Shared in-memory store among all instances of @TokenManagementService
     */
    public static final Map<Users, String> tokenStore = new HashMap<>();

    public void saveUserToken(Users user, String token) {
        tokenStore.put(user, token);
        logger.info(user.getUsername() + " is saved in tokenStore: " + tokenStore);
    }

    public boolean isTokenValid(String token) {
        return tokenStore.containsValue(token);
    }

    public static <U, T> U getUserByToken(Map<U, T> userTokenMap, T token) {
        for (Map.Entry<U, T> entry : userTokenMap.entrySet()) {
            if (Objects.equals(token, entry.getValue())) {
                logger.info(" User token is active.");
                return entry.getKey();
            }
        }
        return null;
    }
}
