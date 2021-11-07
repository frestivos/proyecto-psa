package com.aninfo.controller;

import com.aninfo.model.Account;
import com.aninfo.service.AccountServiceDecorated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class DepositControllerTest {

    @InjectMocks
    private DepositController depositController;

    @Mock
    private AccountServiceDecorated accountService;

    @Mock
    private Account expectedAccount;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void deposit() {
        Long cbu = 123L;
        Double sum = 321D;

        when(this.accountService.deposit(cbu, sum)).thenReturn(this.expectedAccount);

        Account actualAccount = this.depositController.deposit(cbu, sum);

        assertSame(this.expectedAccount, actualAccount);
        verify(this.accountService).deposit(cbu, sum);
        verifyNoMoreInteractions(this.accountService);
    }
}
