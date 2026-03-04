package com.susan.digitalbanking.digital_banking_api.serviceImpl;

import com.susan.digitalbanking.digital_banking_api.entity.*;
import com.susan.digitalbanking.digital_banking_api.repository.AccountTransactionRepository;
import com.susan.digitalbanking.digital_banking_api.repository.BankAccountRepositoy;
import com.susan.digitalbanking.digital_banking_api.repository.CustomerRepository;
import com.susan.digitalbanking.digital_banking_api.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final BankAccountRepositoy accountRepositoy;
    private final CustomerRepository customerRepository;
    private final AccountTransactionRepository transactionRepository;


    //open account
    @Override
    public BankAccount openAccount(Long customerId, AccountType type, BigDecimal initialBalance) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new RuntimeException("Customer not found"));

        //Create account with unique ID
        BankAccount account = new BankAccount(
                "ACC-" + System.currentTimeMillis(),
                initialBalance,
                type,
                customer
        );

        return accountRepositoy.save(account);
    }

    //deposit money
    @Override
    public void deposit(String accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 ){
            throw new RuntimeException("Deposit amount must be positive");
        }

        BankAccount account = accountRepositoy.findById(accountId)
                .orElseThrow(()-> new RuntimeException("Account not found"));

        //Update balance
        account.setBalance(account.getBalance().add(amount));

        //Log the transaction
        transactionRepository.save(new AccountTransaction(
                null,
                amount,
                LocalDateTime.now(),
                TransactionType.DEPOSIT,
                account
        ));


    }

    //withdraw money
    @Override
    public void withdraw(String accountId, BigDecimal amount) {

        BankAccount account = accountRepositoy.findById(accountId)
                .orElseThrow(()-> new RuntimeException("Account not found"));

        if(account.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient balance");
        }

        //update the balance
        account.setBalance(account.getBalance().subtract(amount));

        //Log the transaction
        transactionRepository.save(new AccountTransaction(
                null,
                amount,
                LocalDateTime.now(),
                TransactionType.WITHDRAWAL,
                account
        ));

    }

    @Override
    public List<AccountTransaction> getAccountTransactions(String accountId) {
        return List.of();
    }
}
