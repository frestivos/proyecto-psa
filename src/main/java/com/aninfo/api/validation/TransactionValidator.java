package com.aninfo.api.validation;

import com.aninfo.api.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidator {

    @Autowired
    private Validator validator;

    public void validate(Long cbu, TransactionRequest transactionRequest) {
        this.validator.validateCbu(cbu);
        this.validator.validateDestinationCbu(transactionRequest.getDestinationCbu());
        this.validator.validateNotEquals(cbu, transactionRequest.getDestinationCbu(), "The origin CBU could not be the same as the destination one.");
        this.validator.validateSum(transactionRequest.getSum());
        this.validator.validateTransactionType(transactionRequest.getType());
    }

    public void validate(Long cbu) {
        this.validator.validateCbu(cbu);
    }
}
