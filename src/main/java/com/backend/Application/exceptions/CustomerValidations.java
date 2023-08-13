package com.backend.Application.exceptions;

import com.backend.Application.model.Customer;
import jakarta.ws.rs.core.Response;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomerValidations extends Exception {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomerValidations.class);

    public void customerValidations(Customer customer) throws BackendException {
        customerNameValidation(customer.getCustomerName());
        phoneNumberValidation(customer.getPhoneNumber());
    }

    public void customerNameValidation(String customerName) throws BackendException {
        // Regular expression to check if the name contains only letters and spaces
        String regex = "^[a-zA-Z ]+$";

        if (!customerName.matches(regex)) {
            logger.error("Customer name should not contain special characters or numbers.");
            throw new BackendException(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Customer name should not contain special characters or numbers.");
        }
    }

    private static void phoneNumberValidation(long phoneNumber) {
        String phoneNumberStr = String.valueOf(phoneNumber);

        // Validate size
        if (phoneNumberStr.length() != 10) {
            logger.error("Phone number must be 10 digits long.");
            throw new BackendException(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Phone number must be 10 digits long.");
        }

        // Validate first digit
        char firstDigit = phoneNumberStr.charAt(0);
        if (firstDigit != '6' && firstDigit != '7' && firstDigit != '8' && firstDigit != '9') {
            logger.error("Phone number must start with 6, 7, 8, or 9.");
            throw new BackendException(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Phone number must start with 6, 7, 8, or 9.");
        }

        // Validate digits only
        Pattern pattern = Pattern.compile("\\d{10}");
        Matcher matcher = pattern.matcher(phoneNumberStr);
        if (!matcher.matches()) {
            logger.error("Phone number must consist of digits only.");
            throw new BackendException(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Phone number must consist of digits only.");
        }
    }
}
