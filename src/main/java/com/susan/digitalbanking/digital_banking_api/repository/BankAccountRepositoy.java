package com.susan.digitalbanking.digital_banking_api.repository;

import com.susan.digitalbanking.digital_banking_api.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepositoy extends JpaRepository<BankAccount, String> {

}
