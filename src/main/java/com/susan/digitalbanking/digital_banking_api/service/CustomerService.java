package com.susan.digitalbanking.digital_banking_api.service;

import com.susan.digitalbanking.digital_banking_api.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getCustomer(Long id);
    Customer updateCustomer(Long id, String newName, String newEmail);
    List<Customer> getAllCustomers();

}
