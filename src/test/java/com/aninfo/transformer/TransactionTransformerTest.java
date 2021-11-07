package com.aninfo.transformer;

import com.aninfo.api.request.TransactionRequest;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.aninfo.model.TransactionType.DEPOSIT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class TransactionTransformerTest {

    private static final Long CBU = 123456789L;
    private static final Long DESTINATION_CBU = 987654321L;
    private static final String TRANSACTION_TYPE = "DEPOSIT";
    private static final String UNKNOWN_TRANSACTION_TYPE = "UNKNOWN";
    private static final Double SUM = 456D;

    private TransactionTransformer transactionTransformer;

    @Mock
    private TransactionRequest transactionRequest;

    @BeforeEach
    void setUp() {
        initMocks(this);

        this.transactionTransformer = new TransactionTransformer();
    }

    @Test
    void transform() {
        when(this.transactionRequest.getType()).thenReturn(TRANSACTION_TYPE);
        when(this.transactionRequest.getDestinationCbu()).thenReturn(DESTINATION_CBU);
        when(this.transactionRequest.getSum()).thenReturn(SUM);

        Transaction transaction = this.transactionTransformer.transform(CBU, this.transactionRequest);

        assertEquals(CBU, transaction.getCbu());
        assertEquals(DESTINATION_CBU, transaction.getDestinationCbu());
        assertEquals(SUM, transaction.getSum());
        assertEquals(DEPOSIT, transaction.getType());
        assertNull(transaction.getId());
    }

    @Test
    void transform_withUnknownOrNullNorEmptyTransactionTypeThrowsInvalidTransactionTypeException() {
        // With Unknown transaction type throws an InvalidTransactionTypeException
        when(this.transactionRequest.getType()).thenReturn(UNKNOWN_TRANSACTION_TYPE);
        InvalidTransactionTypeException exceptionWithUnknownType = assertThrows(InvalidTransactionTypeException.class,
                () -> this.transactionTransformer.transform(CBU, this.transactionRequest));
        assertEquals("Invalid transaction type.", exceptionWithUnknownType.getMessage());

        // With null transaction type throws an InvalidTransactionTypeException
        when(this.transactionRequest.getType()).thenReturn(null);
        InvalidTransactionTypeException exceptionWithNullType = assertThrows(InvalidTransactionTypeException.class,
                () -> this.transactionTransformer.transform(CBU, this.transactionRequest));
        assertEquals("Invalid transaction type.", exceptionWithNullType.getMessage());

        // With empty transaction type throws an InvalidTransactionTypeException
        when(this.transactionRequest.getType()).thenReturn("");
        InvalidTransactionTypeException exceptionWithEmptyType = assertThrows(InvalidTransactionTypeException.class,
                () -> this.transactionTransformer.transform(CBU, this.transactionRequest));
        assertEquals("Invalid transaction type.", exceptionWithEmptyType.getMessage());
    }
}
