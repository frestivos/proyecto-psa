package com.aninfo.controller;

import com.aninfo.api.request.AccountRequest;
import com.aninfo.api.validation.AccountValidator;
import com.aninfo.model.Account;
import com.aninfo.service.AccountServiceDecorated;
import com.aninfo.transformer.AccountTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class AccountController {

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private AccountTransformer accountTransformer;

    @Autowired
    private AccountServiceDecorated accountService;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody AccountRequest accountRequest) {
        this.accountValidator.validate(accountRequest);
        var account = this.accountTransformer.transform(accountRequest);
        return this.accountService.createAccount(account);
    }

    @GetMapping("/accounts")
    public Collection<Account> getAccounts() {
        return this.accountService.getAccounts();
    }

    @GetMapping("/accounts/{cbu}")
    public Account getAccount(@PathVariable Long cbu) {
        this.accountValidator.validate(cbu);
        return accountService.findByCbu(cbu);
    }

    @PutMapping("/accounts/{cbu}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long cbu,
                                                 @RequestBody Account account) {
        this.accountValidator.validate(cbu);
        this.accountService.update(cbu, account);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/accounts/{cbu}")
    public Account deleteAccount(@PathVariable Long cbu) {
        this.accountValidator.validate(cbu);
        return this.accountService.deleteByCbu(cbu);
    }
}
