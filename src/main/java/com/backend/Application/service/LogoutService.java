package com.backend.Application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogoutService.class);

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        jwt = authHeader.substring(7);
        var storedUserToken = TokenManagementService.getUserByToken(TokenManagementService.tokenStore, jwt);
        logger.info("Logged out successfully");
        if (storedUserToken != null) {
            TokenManagementService.tokenStore.remove(storedUserToken);
        }
    }
}
