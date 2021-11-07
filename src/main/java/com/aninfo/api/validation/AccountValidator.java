package com.aninfo.api.validation;

import com.aninfo.api.request.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountValidator {

    @Autowired
    private Validator validator;

    public void validate(AccountRequest accountRequest) {
        this.validator.validateCbu(accountRequest.getCbu());
        this.validator.validateCurrency(accountRequest.getCurrency());
        this.validator.validateName(accountRequest.getName());
    }

    public void validate(Long cbu) {
        this.validator.validateCbu(cbu);
    }
}
