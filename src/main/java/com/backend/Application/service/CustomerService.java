package com.backend.Application.service;

import com.backend.Application.exceptions.BackendException;
import com.backend.Application.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer registerCustomer(Customer customer) throws BackendException;

    List<Customer> getCustomers();

    Customer updateCustomerByUsername(String username) throws Exception;

    Customer getCustomerByID(int id);

    Customer getCustomerByEmail(String email) throws Exception;
}