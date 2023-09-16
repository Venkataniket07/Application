package com.backend.Application.controller;

import com.backend.Application.exceptions.BackendException;
import com.backend.Application.model.Address;
import com.backend.Application.service.AddressService;
import com.backend.Application.util.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("backendApplication/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    private String extractJwtToken(String token) {
        return token.substring("Bearer ".length());
    }

    @GetMapping("greetings")
    public ResponseEntity<String> greetings() {
        return ResponseEntity.ok("Hello mother-fucker");
    }

    @GetMapping("viewUserAddresses")
    public ResponseEntity<Object> viewUserAddresses(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = extractJwtToken(token);
            return ResponseEntity.ok(addressService.viewUserAddresses(jwtToken));
        } catch (BackendException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getStatus(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping("/updateAddress/{addressId}")
    public ResponseEntity<Object> updateUserAddress(@RequestHeader("Authorization") String token, @PathVariable("addressId") @Valid int addressId, @RequestBody @Valid Address address) {
        try {
            String jwtToken = extractJwtToken(token);
            return ResponseEntity.ok(addressService.updateUserAddress(jwtToken, addressId, address));
        } catch (BackendException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getStatus(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping("addAddress")
    public ResponseEntity<Object> addUserAddress(@RequestHeader("Authorization") String token, @RequestBody @Valid Address address) {
        try {
            String jwtToken = extractJwtToken(token);
            return ResponseEntity.ok(addressService.addAddress(jwtToken, address));
        } catch (BackendException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getStatus(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
}