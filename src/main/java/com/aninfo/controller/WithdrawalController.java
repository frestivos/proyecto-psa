package com.aninfo.controller;

import com.aninfo.model.Account;
import com.aninfo.service.AccountServiceDecorated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WithdrawalController {

    @Autowired
    private AccountServiceDecorated accountService;

    @PutMapping("/accounts/{cbu}/withdraw")
    public Account withdraw(@PathVariable Long cbu,
                            @RequestParam Double sum) {
        return this.accountService.withdraw(cbu, sum);
    }
}
