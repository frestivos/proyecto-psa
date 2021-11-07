package com.aninfo.transformer;

import com.aninfo.api.request.AccountRequest;
import com.aninfo.exceptions.InvalidCurrencyCodeException;
import com.aninfo.model.Account;
import com.aninfo.model.Currency;
import org.springframework.stereotype.Component;

import java.util.Random;

import static org.apache.commons.lang3.EnumUtils.isValidEnum;

@Component
public class AccountTransformer {

    public Account transform(AccountRequest accountRequest) {
        String currency = accountRequest.getCurrency();
        if(!isValidEnum(Currency.class, currency)) {
            throw new InvalidCurrencyCodeException("Invalid currency code.");
        }

        return new Account(accountRequest.getCbu(),
                Currency.valueOf(currency),
                accountRequest.getName(),
                this.randomBalance());
    }

    private Double randomBalance() {
        double start = 500;
        double end = 15000;
        double random = new Random().nextDouble();
        return start + (random * (end - start));
    }
}
