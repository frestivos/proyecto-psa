package com.aninfo.api.validation;

import com.aninfo.api.request.AccountRequest;
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

class AccountValidatorTest {

    private static final Long CBU = 123456789L;
    private static final String ALIAS = "mmcfly";
    private static final String CURRENCY = "Some currency code.";
    private static final String ACCOUNT_NAME = "Some acccount name.";

    @InjectMocks
    private AccountValidator accountValidator;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private Validator validator;

    @Mock
    private AccountRequest accountRequest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void validate() {
        assertRequestValidationException(null, ALIAS, CURRENCY, ACCOUNT_NAME, "CBU could not be null or lower than zero.");
        assertRequestValidationException(-1L, ALIAS, CURRENCY, ACCOUNT_NAME, "CBU could not be null or lower than zero.");
        assertRequestValidationException(0L, ALIAS, CURRENCY, ACCOUNT_NAME, "CBU could not be null or lower than zero.");

        assertRequestValidationException(CBU, null, CURRENCY, ACCOUNT_NAME, "The account alias should not be null nor empty.");
        assertRequestValidationException(CBU, "", CURRENCY, ACCOUNT_NAME, "The account alias should not be null nor empty.");
        assertRequestValidationException(CBU, "asdfghjklqwe", CURRENCY, ACCOUNT_NAME, "The account alias could not exceed 10 characters.");
        assertRequestValidationException(CBU, "12jso22oj", CURRENCY, ACCOUNT_NAME, "The account alias should only have alphabetic characters.");

        assertRequestValidationException(CBU, ALIAS, null, ACCOUNT_NAME, "The currency could not be null nor empty.");
        assertRequestValidationException(CBU, ALIAS, "", ACCOUNT_NAME, "The currency could not be null nor empty.");

        assertRequestValidationException(CBU, ALIAS, CURRENCY, null, "The account name type could not be null nor empty.");
        assertRequestValidationException(CBU, ALIAS, CURRENCY, "", "The account name type could not be null nor empty.");

        loadAccountRequest(CBU, ALIAS, CURRENCY, ACCOUNT_NAME);
        this.accountValidator.validate(accountRequest);
    }

    @Test
    void validate_fromCbu() {
        assertRequestValidationException(null, "CBU could not be null or lower than zero.");
        assertRequestValidationException(-1L, "CBU could not be null or lower than zero.");
        assertRequestValidationException(0L, "CBU could not be null or lower than zero.");

        this.accountValidator.validate(CBU);
    }

    private void assertRequestValidationException(Long cbu,
                                                  String alias,
                                                  String currency,
                                                  String name,
                                                  String expectedMessage) {
        loadAccountRequest(cbu, alias, currency, name);
        RequestValidationException exception = assertThrows(RequestValidationException.class,
                () -> this.accountValidator.validate(accountRequest));
        assertEquals(expectedMessage, exception.getMessage());
    }

    private void loadAccountRequest(Long cbu,
                                    String alias,
                                    String currency,
                                    String name) {
        when(this.accountRequest.getCbu()).thenReturn(cbu);
        when(this.accountRequest.getAlias()).thenReturn(alias);
        when(this.accountRequest.getCurrency()).thenReturn(currency);
        when(this.accountRequest.getName()).thenReturn(name);
    }

    private void assertRequestValidationException(Long cbu, String expectedMessage) {
        RequestValidationException exception = assertThrows(RequestValidationException.class,
                () -> this.accountValidator.validate(cbu));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
