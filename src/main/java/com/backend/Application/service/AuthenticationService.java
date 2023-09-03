package com.backend.Application.service;

import com.backend.Application.exceptions.BackendException;
import com.backend.Application.model.Address;
import com.backend.Application.model.Users;
import com.backend.Application.repository.UserRepository;
import com.backend.Application.requests.AuthenticationRequest;
import com.backend.Application.requests.UserRequest;
import com.backend.Application.util.AuthenticationResponse;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final TokenManagementService tokenManagementService;

    public AuthenticationResponse register(UserRequest userRequest) {

        var user = Users.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .phoneNumber(userRequest.getPhoneNumber())
                .addresses(userRequest.getAddresses())
                .roles(userRequest.getRoles())
                .userRegisterDate(Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata")).getTime())
                .build();
        for (Address address : userRequest.getAddresses()) {
            address.setUsers(user);
        }
        var savedUser = userRepository.save(user);
        logger.info(savedUser.getUsername() + " is register.");
        var jwtToken = jwtService.generateToken(user);
        logger.info(user.getUsername() + "'s generated token: '" + jwtToken + "'");
        tokenManagementService.saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = userRepository.findByUsernameOrEmail(request.getUsername(), request.getUsername())
                .orElseThrow(() -> new BackendException(Response.Status.NOT_FOUND.getStatusCode(), request.getUsername() + " doesn't exists in our database. Please register the User or Provide correct credentials"));

        var jwtToken = jwtService.generateToken(user);
        logger.info(request.getUsername() + " logged in successfully");
        tokenManagementService.saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
