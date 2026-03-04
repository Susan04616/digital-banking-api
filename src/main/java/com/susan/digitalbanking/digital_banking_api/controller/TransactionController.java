package com.susan.digitalbanking.digital_banking_api.controller;


import com.susan.digitalbanking.digital_banking_api.entity.AccountTransaction;
import com.susan.digitalbanking.digital_banking_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final AccountService accountService;

    // ------------------ Get all transactions for an account ------------------
    @GetMapping("/{accountId}")
    public List<AccountTransaction> getTransactions(@PathVariable String accountId) {
        return accountService.getAccountTransactions(accountId);
    }
}
