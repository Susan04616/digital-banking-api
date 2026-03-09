package com.susan.digitalbanking.digital_banking_api.mapper;

import com.susan.digitalbanking.digital_banking_api.dto.CustomerDTO;
import com.susan.digitalbanking.digital_banking_api.entity.Customer;

public class CustomerMapper {

    public static CustomerDTO toDTO (Customer customer){
        CustomerDTO dto = new CustomerDTO();

        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());

        return dto;
    }

    public static Customer toEntity(CustomerDTO dto){
        Customer customer = new Customer();

        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());

        return customer;
    }

    }
}
