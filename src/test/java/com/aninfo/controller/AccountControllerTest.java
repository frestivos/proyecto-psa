package com.aninfo.controller;

import com.aninfo.api.request.AccountRequest;
import com.aninfo.api.validation.AccountValidator;
import com.aninfo.model.Account;
import com.aninfo.service.AccountServiceDecorated;
import com.aninfo.transformer.AccountTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class AccountControllerTest {

    private static final Long CBU = 123456789L;

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountValidator accountValidator;

    @Mock
    private AccountTransformer accountTransformer;

    @Mock
    private AccountServiceDecorated accountService;

    @Mock
    private AccountRequest accountRequest;

    @Mock
    private Account account, otherAccount, expectedAccount;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void createAccount() {
        doNothing().when(this.accountValidator).validate(this.accountRequest);
        when(this.accountTransformer.transform(this.accountRequest)).thenReturn(this.account);
        when(this.accountService.createAccount(this.account)).thenReturn(this.expectedAccount);

        Account actualAccount = this.accountController.createAccount(this.accountRequest);

        assertSame(this.expectedAccount, actualAccount);
        verify(this.accountValidator).validate(this.accountRequest);
        verify(this.accountTransformer).transform(this.accountRequest);
        verify(this.accountService).createAccount(this.account);
    }

    @Test
    void getAccounts() {
        // Could not retrieve any account
        when(this.accountService.getAccounts()).thenReturn(new ArrayList<>());
        assertTrue(this.accountController.getAccounts().isEmpty());

        // Retrieve 2 accounts
        when(this.accountService.getAccounts()).thenReturn(List.of(this.account, this.otherAccount));
        Collection<Account> accounts = this.accountController.getAccounts();
        assertEquals(2, accounts.size());
        assertTrue(accounts.contains(this.account));
        assertTrue(accounts.contains(this.otherAccount));
    }

    @Test
    void getAccount() {
        doNothing().when(this.accountValidator).validate(CBU);
        when(this.accountService.findByCbu(CBU)).thenReturn(this.expectedAccount);

        Account actualAccount = this.accountController.getAccount(CBU);

        assertSame(this.expectedAccount, actualAccount);
        verify(this.accountValidator).validate(CBU);
        verify(this.accountService).findByCbu(CBU);
    }

    @Test
    void updateAccount() {
        doNothing().when(this.accountValidator).validate(CBU);
        doNothing().when(this.accountService).update(CBU, this.account);

        ResponseEntity<Account> accountResponseEntity = this.accountController.updateAccount(CBU, this.account);

        assertEquals(HttpStatus.NO_CONTENT, accountResponseEntity.getStatusCode());
        assertTrue(accountResponseEntity.getHeaders().isEmpty());
        assertNull(accountResponseEntity.getBody());
        verify(this.accountValidator).validate(CBU);
        verify(this.accountService).update(CBU, this.account);

    }

    @Test
    void deleteAccount() {
        doNothing().when(this.accountValidator).validate(CBU);
        when(this.accountService.deleteByCbu(CBU)).thenReturn(this.expectedAccount);

        Account actualAccount = this.accountController.deleteAccount(CBU);

        assertSame(this.expectedAccount, actualAccount);
        verify(this.accountValidator).validate(CBU);
        verify(this.accountService).deleteByCbu(CBU);
    }
}
