package com.backend.Application.implementation;

import com.backend.Application.exceptions.BackendException;
import com.backend.Application.model.Address;
import com.backend.Application.repository.AddressRepository;
import com.backend.Application.repository.UserRepository;
import com.backend.Application.service.AddressService;
import com.backend.Application.service.JwtService;
import com.backend.Application.util.BackendResponse;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImplementation implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImplementation.class.getName());

    @Override
    public BackendResponse updateUserAddress(String token, int addressId, Address address) {
        try {
            // Retrieve the address to be updated
            Optional<Address> optionalAddress = addressRepository.findById(addressId);
            Address existingAddress = getExistingAddress(address, optionalAddress);

            addressRepository.save(existingAddress);
            return BackendResponse.builder()
                    .response("Address updated. To view addresses click on: http://localhost:8080/backendApplication/address/viewUserAddresses").build();
        } catch (Exception e) {
            logger.error("Error occurred while updating address: " + e.getMessage());
            throw e;
        }
    }

    private static Address getExistingAddress(Address address, Optional<Address> optionalAddress) {
        Address existingAddress = optionalAddress.orElseThrow(() -> new BackendException(Response.Status.NOT_FOUND.getStatusCode(), "Invalid address Id. Please provide the correct addressId to update your address."));

        existingAddress.setApartment(address.getApartment());
        existingAddress.setLocality(address.getLocality());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setCountry(address.getCountry());
        existingAddress.setPinCode(address.getPinCode());
        return existingAddress;
    }

    @Override
    public BackendResponse addAddress(String token, Address address) {
        try {
            var user = userRepository.findByUsernameOrEmail(jwtService.extractUsername(token), jwtService.extractUsername(token))
                    .orElseThrow(() -> new BackendException(Response.Status.UNAUTHORIZED.getStatusCode(), "Please login to access this feature."));

            // Set the user as the owner of the new address
            address.setUsers(user);
            addressRepository.save(address);

            return BackendResponse.builder()
                    .response("Address added. To view addresses click on: http://localhost:8080/backendApplication/address/viewUserAddresses").build();
        } catch (Exception e) {
            logger.error("Error occurred while adding address: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Address> viewUserAddresses(String token) throws BackendException {
        try {
            var user = userRepository.findByUsernameOrEmail(jwtService.extractUsername(token), jwtService.extractUsername(token))
                    .orElseThrow(() -> new BackendException(Response.Status.UNAUTHORIZED.getStatusCode(), "Please login again to view your addresses"));

            return addressRepository.findAddressesByUserId(user.getId());
        } catch (Exception e) {
            logger.error("Error occurred while viewing user addresses: " + e.getMessage());
            throw e;
        }
    }
}