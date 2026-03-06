package com.susan.digitalbanking.digital_banking_api.service;

import com.susan.digitalbanking.digital_banking_api.entity.AccountTransaction;
import com.susan.digitalbanking.digital_banking_api.entity.AccountType;
import com.susan.digitalbanking.digital_banking_api.entity.BankAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountService {
    // Open a new account
    BankAccount openAccount(Long customerId, AccountType type, BigDecimal initialBalance);

    // Deposit money → now returns the updated account
    BankAccount deposit(String accountId, BigDecimal amount);

    // Withdraw money → now returns the updated account
    BankAccount withdraw(String accountId, BigDecimal amount);

    //Transfer money between accounts : return both updated accounts
    Map<String, BankAccount> transfer(String fromAccountId, String toAccountId, BigDecimal amount);

    // Get all transactions for an account
    List<AccountTransaction> getAccountTransactions(String accountId);
}