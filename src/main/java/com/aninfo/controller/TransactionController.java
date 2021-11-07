package com.aninfo.controller;

import com.aninfo.api.request.TransactionRequest;
import com.aninfo.api.validation.TransactionValidator;
import com.aninfo.model.Transaction;
import com.aninfo.service.TransactionServiceDecorated;
import com.aninfo.transformer.TransactionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class TransactionController {

    @Autowired
    private TransactionValidator transactionValidator;

    @Autowired
    private TransactionServiceDecorated transactionService;

    @Autowired
    private TransactionTransformer transactionTransformer;

    @PostMapping("/accounts/{cbu}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@PathVariable Long cbu,
                                         @RequestBody TransactionRequest transactionRequest) {
        this.transactionValidator.validate(cbu, transactionRequest);
        var transaction = this.transactionTransformer.transform(cbu, transactionRequest);
        return this.transactionService.createTransaction(transaction);
    }

    @GetMapping("/accounts/{cbu}/transactions")
    public Collection<Transaction> getTransactions(@PathVariable Long cbu) {
        this.transactionValidator.validate(cbu);
        return this.transactionService.getTransactions(cbu);
    }
}
