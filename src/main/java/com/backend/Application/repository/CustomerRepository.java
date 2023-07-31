package com.backend.Application.repository;

import com.backend.Application.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.userCredentials.username = :username")
    Optional<Customer> findCustomerByUsername(@Param("username") String username);

    @Query("SELECT c FROM Customer c WHERE c.userCredentials.email = :email")
    Optional<Customer> findCustomerByEmail(@Param(("email")) String email);
}