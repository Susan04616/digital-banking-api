package com.susan.digitalbanking.digital_banking_api.controller;

import com.susan.digitalbanking.digital_banking_api.entity.AccountType;
import com.susan.digitalbanking.digital_banking_api.entity.BankAccount;
import com.susan.digitalbanking.digital_banking_api.service.AccountService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

    //Deposit money
    @PostMapping("/{accountId}/deposit")
    public void deposit(
            @PathVariable String accountId,
            @RequestParam BigDecimal amount
    ){
        accountService.deposit(accountId, amount);

    }
    @PostMapping("/{accountId}/withdraw")
    public void withdraw(
            @PathVariable String accountId,
            @RequestParam BigDecimal amount
    ){
        accountService.withdraw(accountId, amount);
    }

}
