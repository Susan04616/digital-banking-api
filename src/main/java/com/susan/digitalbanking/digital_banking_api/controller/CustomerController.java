package com.susan.digitalbanking.digital_banking_api.controller;

import com.susan.digitalbanking.digital_banking_api.entity.Customer;
import com.susan.digitalbanking.digital_banking_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // ------------------ Create Customer ------------------
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        // @RequestBody converts JSON into Customer object
        return customerService.createCustomer(customer);
    }

    // ------------------ Get Customer by ID ------------------
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    // ------------------ Get All Customers ------------------
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // ------------------ Update Customer (name & email only) ------------------
    @PutMapping("/{id}")
    public Customer updateCustomer(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String email
    ) {
        return customerService.updateCustomer(id, name, email);
    }
}
