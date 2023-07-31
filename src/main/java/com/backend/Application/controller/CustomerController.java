package com.backend.Application.controller;

import com.backend.Application.model.Customer;
import com.backend.Application.model.UserCredentials;
import com.backend.Application.service.CustomerService;
import com.backend.Application.service.UserCredentialsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backendApplication/Customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    UserCredentialsService userCredentialsService;

    @PostMapping("register")
    public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody Customer customer) throws Exception {
        try {
            return new ResponseEntity<>(customerService.registerCustomer(customer), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Bad request");
        }
    }

    @PostMapping("login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserCredentials userCredentials) throws Exception {
        UserCredentials loggedReturn = userCredentialsService.login(userCredentials.getUsername(), userCredentials.getPassword());
        if (loggedReturn != null) {
            return new ResponseEntity<>(userCredentials.getUsername() + " Logged in successfully!!", HttpStatus.OK);
        } else
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("home")
    public String home() {
        return "Welcome to Home Page";
    }

    @GetMapping("greetings/{username}")
    public String greetings(@PathVariable String username) {
        return "Hello " + username + "!";
    }
}
