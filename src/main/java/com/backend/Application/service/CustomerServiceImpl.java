package com.backend.Application.service;

import com.backend.Application.exceptions.BackendException;
import com.backend.Application.exceptions.CustomerValidations;
import com.backend.Application.exceptions.UserCredentialsValidations;
import com.backend.Application.model.Address;
import com.backend.Application.model.Customer;
import com.backend.Application.model.UserCredentials;
import com.backend.Application.repository.CustomerRepository;
import com.backend.Application.repository.UserCredentialsRepository;
import jakarta.ws.rs.core.Response;
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
    @Autowired
    private CustomerValidations customerValidations;
    @Autowired
    private UserCredentialsValidations userCredentialsValidations;

    public Customer registerCustomer(Customer customer) throws BackendException {
        try {
            UserCredentials userCredentials = customer.getUserCredentials();

            // Validating customer and user-credentials
            customerValidations.customerValidations(customer);
            userCredentialsValidations.userCredentialsValidations(userCredentials);

            // Saving UserCredentials
            userCredentialsRepository.save(userCredentials);

            //saving user address.
            for (Address address : customer.getAddresses()) {
                address.setCustomer(customer);
            }
            Customer savedCustomer = customerRepository.save(customer);
            logger.info("Registering customer: {}", savedCustomer);

            return savedCustomer;
        } catch (BackendException e) {
            logger.error(e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            throw new BackendException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "User can't be registered.");
        }
    }

    @Override
    public List<Customer> getCustomers() {
        logger.info("Getting all customers");
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomerByUsername(String username) throws Exception {
        logger.info("Updating customer with username: {}", username);
        Customer existingCustomer = customerRepository.findCustomerByUsername(username).orElseThrow(() -> new Exception("Customer not found with username: " + username));
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
        return customerRepository.findCustomerByEmail(email).orElseThrow(() -> new Exception("Customer not found with email: " + email));
    }
}