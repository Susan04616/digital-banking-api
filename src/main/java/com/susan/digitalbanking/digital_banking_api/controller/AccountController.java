package com.susan.digitalbanking.digital_banking_api.controller;

import com.susan.digitalbanking.digital_banking_api.entity.AccountTransaction;
import com.susan.digitalbanking.digital_banking_api.entity.AccountType;
import com.susan.digitalbanking.digital_banking_api.entity.BankAccount;
import com.susan.digitalbanking.digital_banking_api.service.AccountService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {

    public final AccountService accountService;

    @PostMapping
    public BankAccount openAccount(
            @RequestParam Long customerId,
            @RequestParam AccountType type,
            @RequestParam BigDecimal balance
    ){
        return accountService.openAccount(customerId, type, balance);
    }

    //deposit
    @PostMapping("/{accountId}/deposit")
    public BankAccount deposit(
            @PathVariable String accountId,
            @RequestParam BigDecimal amount
    ){
        // Return the updated account
        return accountService.deposit(accountId, amount);
    }

    //withdraw
    @PostMapping("/{accountId}/withdraw")
    public BankAccount withdraw(
            @PathVariable String accountId,
            @RequestParam BigDecimal amount
    ){
        // Return the updated account
        return accountService.withdraw(accountId, amount);
    }

    //transfer
    @PostMapping("/transfer")
    public Map<String, BankAccount> transfer(
            @RequestParam String fromAccountId,
            @RequestParam String toAccountId,
            @RequestParam BigDecimal amount
    ){
        return accountService.transfer(fromAccountId, toAccountId, amount);
    }

    //get transactions
    @GetMapping("/{accountId}/transactions")
    public List<AccountTransaction> getTransactions(@PathVariable String accountId){
        return accountService.getAccountTransactions(accountId);
    }

}
