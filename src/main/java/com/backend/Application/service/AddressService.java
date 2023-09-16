package com.backend.Application.service;

import com.backend.Application.exceptions.BackendException;
import com.backend.Application.model.Address;
import com.backend.Application.util.BackendResponse;

import java.util.List;

public interface AddressService {
    BackendResponse updateUserAddress(String token, int addressId, Address address) throws BackendException;

    BackendResponse addAddress(String token, Address address) throws BackendException;

    List<Address> viewUserAddresses(String token) throws BackendException;
}
