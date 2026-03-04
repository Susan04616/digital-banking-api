package com.susan.digitalbanking.digital_banking_api.repository;

import com.susan.digitalbanking.digital_banking_api.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long > {
}
