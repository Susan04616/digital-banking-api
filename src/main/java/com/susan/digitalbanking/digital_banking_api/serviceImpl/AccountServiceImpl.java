package com.susan.digitalbanking.digital_banking_api.serviceImpl;

import com.susan.digitalbanking.digital_banking_api.entity.*;
import com.susan.digitalbanking.digital_banking_api.repository.AccountTransactionRepository;
import com.susan.digitalbanking.digital_banking_api.repository.BankAccountRepository;
import com.susan.digitalbanking.digital_banking_api.repository.CustomerRepository;
import com.susan.digitalbanking.digital_banking_api.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final BankAccountRepository accountRepositoy;
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
    public BankAccount deposit(String accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 ){
            throw new RuntimeException("Deposit amount must be positive");
        }

        BankAccount account = accountRepositoy.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Update balance
        account.setBalance(account.getBalance().add(amount));

        // Log the transaction
        transactionRepository.save(new AccountTransaction(
                null,
                amount,
                LocalDateTime.now(),
                TransactionType.DEPOSIT,
                account
        ));

        // Return updated account
        return accountRepositoy.save(account);
    }

    @Override
    public BankAccount withdraw(String accountId, BigDecimal amount) {

        BankAccount account = accountRepositoy.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if(account.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient balance");
        }

        // Update balance
        account.setBalance(account.getBalance().subtract(amount));

        // Log the transaction
        transactionRepository.save(new AccountTransaction(
                null,
                amount,
                LocalDateTime.now(),
                TransactionType.WITHDRAWAL,
                account
        ));

        // Return Updated account
        return accountRepositoy.save(account);
    }

    //Transfer money
    @Override
    public Map<String, BankAccount> transfer(String fromAccountId, String toAccountId, BigDecimal amount) {
        BankAccount fromAccount = accountRepositoy.findById(fromAccountId)
                .orElseThrow(()-> new RuntimeException("Source account not found"));
        BankAccount toAccount = accountRepositoy.findById(toAccountId)
                .orElseThrow(()-> new RuntimeException("Destination account not found"));

        if(fromAccount.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient balance for transfer");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        //Log transfer transactions for both accounts
        transactionRepository.save(new AccountTransaction(null, amount, LocalDateTime.now(), TransactionType.TRANSFER, fromAccount));
        transactionRepository.save(new AccountTransaction(null, amount, LocalDateTime.now(), TransactionType.TRANSFER, toAccount));

        //Save updated accounts and a return list
        accountRepositoy.save(fromAccount);
        accountRepositoy.save(toAccount);

        Map<String, BankAccount> result = new HashMap<>();
        result.put("fromAccount", accountRepositoy.findById(fromAccountId).get());
        result.put("toAccount", accountRepositoy.findById(toAccountId).get());

        return result;
    }

    // Transaction history
    @Override
    public List<AccountTransaction> getAccountTransactions(String accountId) {
       BankAccount account = accountRepositoy.findById(accountId)
               .orElseThrow(()-> new RuntimeException("Account not found"));

        //returns all the transactions linked to the account
        return transactionRepository.findByBankAccount(account);
    }

}
