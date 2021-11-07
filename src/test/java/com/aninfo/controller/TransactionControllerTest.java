package com.aninfo.controller;

import com.aninfo.api.request.TransactionRequest;
import com.aninfo.api.validation.TransactionValidator;
import com.aninfo.model.Transaction;
import com.aninfo.service.TransactionServiceDecorated;
import com.aninfo.transformer.TransactionTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TransactionControllerTest {

    private static final Long CBU = 123456789L;

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionValidator transactionValidator;

    @Mock
    private TransactionServiceDecorated transactionService;

    @Mock
    private TransactionTransformer transactionTransformer;

    @Mock
    private TransactionRequest transactionRequest;

    @Mock
    private Transaction transaction, expectedTransaction;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void createTransaction() {
        doNothing().when(this.transactionValidator).validate(CBU, this.transactionRequest);
        when(this.transactionTransformer.transform(CBU, this.transactionRequest)).thenReturn(this.transaction);
        when(this.transactionService.createTransaction(this.transaction)).thenReturn(this.expectedTransaction);

        Transaction actualTransaction = this.transactionController.createTransaction(CBU, this.transactionRequest);

        assertSame(this.expectedTransaction, actualTransaction);
        verify(this.transactionValidator).validate(CBU, this.transactionRequest);
        verify(this.transactionTransformer).transform(CBU, this.transactionRequest);
        verify(this.transactionService).createTransaction(this.transaction);
        verifyNoMoreInteractions(this.transactionTransformer, this.transactionService);
    }

    @Test
    void getTransactions() {
        doNothing().when(this.transactionValidator).validate(CBU);
        when(this.transactionService.getTransactions(CBU)).thenReturn(List.of(this.transaction, this.expectedTransaction));

        Collection<Transaction> transactions = this.transactionController.getTransactions(CBU);

        assertEquals(2, transactions.size());
        verify(this.transactionValidator).validate(CBU);
        assertTrue(transactions.contains(this.transaction));
        assertTrue(transactions.contains(this.expectedTransaction));
    }
}
