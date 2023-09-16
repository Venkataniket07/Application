package com.backend.Application.controller;

import com.backend.Application.exceptions.BackendException;
import com.backend.Application.requests.UpdatePasswordRequest;
import com.backend.Application.requests.UserUpdateRequest;
import com.backend.Application.service.UserService;
import com.backend.Application.util.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("backendApplication/users")
public class UserController {

    @Autowired
    private UserService userService;

    private String extractJwtToken(String token) {
        return token.substring("Bearer ".length());
    }

    @PostMapping("updateUser")
    public ResponseEntity<Object> updateUser(@RequestHeader("Authorization") String token, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        try {
            String jwtToken = extractJwtToken(token);
            return ResponseEntity.ok(userService.updateUser(jwtToken, userUpdateRequest));
        } catch (BackendException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getStatus(), e.getMessage()), HttpStatus.valueOf(e.getStatus()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("updatePassword")
    public ResponseEntity<Object> updatePassword(@RequestHeader("Authorization") String token, @RequestBody @Valid UpdatePasswordRequest updatePasswordRequest) {
        try {
            String jwtToken = extractJwtToken(token);
            return ResponseEntity.ok(userService.updatePassword(updatePasswordRequest, jwtToken));
        } catch (BackendException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getStatus(), e.getMessage()), HttpStatus.valueOf(e.getStatus()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("viewUser")
    public ResponseEntity<Object> viewUser(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = extractJwtToken(token);
            return ResponseEntity.ok(userService.getUserFromToken(jwtToken));
        } catch (BackendException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getStatus(), e.getMessage()), HttpStatus.valueOf(e.getStatus()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
