package com.aninfo.service;

import com.aninfo.exceptions.AccountNotFoundException;
import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
class AccountService {

    private static final String ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE = "The CBU does not have any related account.";
    private static final String INSUFFICIENT_FUNDS_EXCEPTION_MESSAGE = "Insufficient funds";
    private static final String DEPOSIT_NEGATIVE_EXCEPTION_MESSAGE = "Cannot deposit negative sums";

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return this.accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        Collection<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    public Account findByCbu(Long cbu) {
        return this.accountRepository.findAccountByCbu(cbu)
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    public void save(Account account) {
        this.accountRepository.save(account);
    }

    @Transactional
    public void update(Long cbu, Account account) {
        var accountRegistered = this.findByCbu(cbu);
        account.setCbu(cbu);
        account.setName(accountRegistered.getName());
        this.save(account);
    }

    @Transactional
    public Account deleteByCbu(Long cbu) {
        return this.accountRepository.findAccountByCbu(cbu)
                .map(this::deleteAccount)
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Transactional
    public Account withdraw(Long cbu, Double sum) {
        Optional<Account> account = accountRepository.findAccountByCbu(cbu);

        if(account.isEmpty()) {
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        return account.filter(a -> a.getBalance() >= sum)
                .map(a -> {
                    a.setBalance(a.getBalance() - sum);
                    return accountRepository.save(a);
                })
                .orElseThrow(() -> new InsufficientFundsException(INSUFFICIENT_FUNDS_EXCEPTION_MESSAGE));
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {
        if (sum <= 0) {
            throw new DepositNegativeSumException(DEPOSIT_NEGATIVE_EXCEPTION_MESSAGE);
        }

        return accountRepository.findAccountByCbu(cbu)
                .map(account -> {
                    account.setBalance(account.getBalance() + sum);
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    private Account deleteAccount(Account account) {
        this.accountRepository.deleteById(account.getId());
        return account;
    }
}
