package com.susan.digitalbanking.digital_banking_api.serviceImpl;

import com.susan.digitalbanking.digital_banking_api.entity.Customer;
import com.susan.digitalbanking.digital_banking_api.repository.CustomerRepository;
import com.susan.digitalbanking.digital_banking_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;


    //create and save customer in db
    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    //find customer by id
    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(("Customer not found")));
    }

    //Update customer (email, name)
    @Override
    public Customer updateCustomer(Long id, String newName, String newEmail) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Customer not found"));

        //only these fields can be updated
        customer.setName(newName);
        customer.setEmail(newEmail);

        return customerRepository.save(customer);
    }

    //get all customers
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
