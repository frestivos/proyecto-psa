package com.aninfo.transformer;

import com.aninfo.api.request.AccountRequest;
import com.aninfo.exceptions.InvalidCurrencyCodeException;
import com.aninfo.model.Account;
import com.aninfo.model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AccountTransformerTest {

    private static final Long CBU = 123456789L;
    private static final String ALIAS = "mmcfly";
    private static final String CURRENCY = "ARS";
    private static final String ACCOUNT_NAME = "Some acccount name.";

    private final AccountTransformer accountTransformer = new AccountTransformer();

    @Mock
    private AccountRequest accountRequest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void transform() {
        when(this.accountRequest.getCbu()).thenReturn(CBU);
        when(this.accountRequest.getAlias()).thenReturn(ALIAS);
        when(this.accountRequest.getCurrency()).thenReturn(CURRENCY);
        when(this.accountRequest.getName()).thenReturn(ACCOUNT_NAME);

        Account account = this.accountTransformer.transform(this.accountRequest);
        assertAccount(account);
    }

    @Test
    void transform_withAliasAsUpperCase() {
        when(this.accountRequest.getCbu()).thenReturn(CBU);
        when(this.accountRequest.getAlias()).thenReturn(ALIAS.toUpperCase());
        when(this.accountRequest.getCurrency()).thenReturn(CURRENCY);
        when(this.accountRequest.getName()).thenReturn(ACCOUNT_NAME);

        Account account = this.accountTransformer.transform(this.accountRequest);
        assertAccount(account);
    }

    @Test
    void transform_withInvalidCurrencyThrowsInvalidCurrencyCodeException() {
        when(this.accountRequest.getCurrency()).thenReturn("WHAT?");
        InvalidCurrencyCodeException exception = assertThrows(InvalidCurrencyCodeException.class,
                () -> this.accountTransformer.transform(this.accountRequest));
        assertEquals("Invalid currency code.", exception.getMessage());
    }

    private void assertAccount(Account account) {
        assertEquals(CBU, account.getCbu());
        assertEquals(ALIAS, account.getAlias());
        assertEquals(Currency.ARS, account.getCurrency());
        assertEquals(ACCOUNT_NAME, account.getName());
        assertNotNull(account.getBalance());
        assertTrue(account.getBalance() > 0);
    }
}
