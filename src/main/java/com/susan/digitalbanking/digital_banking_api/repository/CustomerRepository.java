package com.susan.digitalbanking.digital_banking_api.repository;

import com.susan.digitalbanking.digital_banking_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
