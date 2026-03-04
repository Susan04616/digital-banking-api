package com.susan.digitalbanking.digital_banking_api.service;

import com.susan.digitalbanking.digital_banking_api.entity.AccountTransaction;
import com.susan.digitalbanking.digital_banking_api.entity.AccountType;
import com.susan.digitalbanking.digital_banking_api.entity.BankAccount;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    //open an account
    BankAccount openAccount(Long customerId, AccountType type, BigDecimal initialBalance);
    //deposit money
    void deposit(String accountId, BigDecimal amount);
    //withdraw money
    void withdraw(String accountId, BigDecimal amount);

    List<AccountTransaction> getAccountTransactions(String accountId);
}
