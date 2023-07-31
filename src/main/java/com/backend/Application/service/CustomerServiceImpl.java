package com.backend.Application.service;

import com.backend.Application.model.Address;
import com.backend.Application.model.Customer;
import com.backend.Application.model.UserCredentials;
import com.backend.Application.repository.CustomerRepository;
import com.backend.Application.repository.UserCredentialsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public Customer registerCustomer(Customer customer) throws Exception {
        UserCredentials userCredentials = customer.getUserCredentials();
        if (emailAlreadyExists(userCredentials)) {
            // Email already exists
            throw new Exception("email already exists!! User another email-id");
        }

        // Saving UserCredentials
        userCredentialsRepository.save(userCredentials);

        //saving user address.
        for (Address address : customer.getAddresses()) {
            address.setCustomer(customer);
        }
        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Registering customer: {}", savedCustomer);

        return savedCustomer;
    }

    @Override
    public List<Customer> getCustomers() {
        logger.info("Getting all customers");
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomerByUsername(String username) throws Exception {
        logger.info("Updating customer with username: {}", username);
        Customer existingCustomer = customerRepository.findCustomerByUsername(username)
                .orElseThrow(() -> new Exception("Customer not found with username: " + username));
        BeanUtils.copyProperties(existingCustomer, existingCustomer, "id", "username");
        return customerRepository.save(existingCustomer);
    }

    @Override
    public Customer getCustomerByID(int id) {
        logger.info("Getting customer by ID: {}", id);
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer getCustomerByEmail(String email) throws Exception {
        logger.info("Getting customer by email: {}", email);
        return customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new Exception("Customer not found with email: " + email));
    }

    private boolean emailAlreadyExists(UserCredentials userCredentials) {
        UserCredentials existingUserCredentialsByEmail = userCredentialsRepository.findByEmail(userCredentials.getEmail());
        return existingUserCredentialsByEmail != null;
    }
}