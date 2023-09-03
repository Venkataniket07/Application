package com.backend.Application.service;

import com.backend.Application.exceptions.BackendException;
import com.backend.Application.model.Address;

import java.util.List;

public interface AddressService {
    Address updateUserAddress(String token, int addressId, Address address) throws BackendException;

    Address addAddress(String token, Address address) throws BackendException;

    List<Address> viewUserAddresses(String token) throws BackendException;
}
