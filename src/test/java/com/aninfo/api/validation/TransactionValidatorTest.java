package com.aninfo.api.validation;

import com.aninfo.api.request.TransactionRequest;
import com.aninfo.exceptions.RequestValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class TransactionValidatorTest {

    private static final Long CBU = 123456789L;
    private static final Long DESTINATION_CBU = 987654321L;
    private static final Double SUM = 456D;
    private static final String TRANSACTION_TYPE = "Some transaction type.";

    @InjectMocks
    private TransactionValidator transactionValidator;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private Validator validator;

    @Mock
    private TransactionRequest transactionRequest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void validate_fromCbuAndRequestTransaction() {
        assertRequestValidationException(null, DESTINATION_CBU, SUM, TRANSACTION_TYPE, "CBU could not be null or lower than zero.");
        assertRequestValidationException(-1L, DESTINATION_CBU, SUM, TRANSACTION_TYPE, "CBU could not be null or lower than zero.");
        assertRequestValidationException(0L, DESTINATION_CBU, SUM, TRANSACTION_TYPE, "CBU could not be null or lower than zero.");

        assertRequestValidationException(CBU, null, SUM, TRANSACTION_TYPE, "Destination CBU could not be null or lower than zero.");
        assertRequestValidationException(CBU, -1L, SUM, TRANSACTION_TYPE, "Destination CBU could not be null or lower than zero.");
        assertRequestValidationException(CBU, 0L, SUM, TRANSACTION_TYPE, "Destination CBU could not be null or lower than zero.");

        assertRequestValidationException(CBU, CBU, SUM, TRANSACTION_TYPE, "The origin CBU could not be the same as the destination one.");

        assertRequestValidationException(CBU, DESTINATION_CBU, null, TRANSACTION_TYPE, "The SUM could not be null or lower than zero.");
        assertRequestValidationException(CBU, DESTINATION_CBU, -1D, TRANSACTION_TYPE, "The SUM could not be null or lower than zero.");
        assertRequestValidationException(CBU, DESTINATION_CBU, 0D, TRANSACTION_TYPE, "The SUM could not be null or lower than zero.");

        assertRequestValidationException(CBU, DESTINATION_CBU, SUM, null, "Transaction type could not be null nor empty.");
        assertRequestValidationException(CBU, DESTINATION_CBU, SUM, "", "Transaction type could not be null nor empty.");

        loadTransactionRequest(DESTINATION_CBU, SUM, TRANSACTION_TYPE);
        this.transactionValidator.validate(CBU, transactionRequest);
    }

    @Test
    void validate_fromCbu() {
        assertRequestValidationException(null, "CBU could not be null or lower than zero.");
        assertRequestValidationException(-1L, "CBU could not be null or lower than zero.");
        assertRequestValidationException(0L, "CBU could not be null or lower than zero.");

        this.transactionValidator.validate(CBU);
    }

    private void assertRequestValidationException(Long cbu,
                                                  Long destinationCbu,
                                                  Double sum,
                                                  String transactionType,
                                                  String expectedMessage) {
        loadTransactionRequest(destinationCbu, sum, transactionType);
        RequestValidationException exception = assertThrows(RequestValidationException.class,
                () -> this.transactionValidator.validate(cbu, transactionRequest));
        assertEquals(expectedMessage, exception.getMessage());
    }

    private void loadTransactionRequest(Long destinationCbu,
                                        Double sum,
                                        String transactionType) {
        when(this.transactionRequest.getDestinationCbu()).thenReturn(destinationCbu);
        when(this.transactionRequest.getSum()).thenReturn(sum);
        when(this.transactionRequest.getType()).thenReturn(transactionType);
    }

    private void assertRequestValidationException(Long cbu, String expectedMessage) {
        RequestValidationException exception = assertThrows(RequestValidationException.class,
                () -> this.transactionValidator.validate(cbu));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
