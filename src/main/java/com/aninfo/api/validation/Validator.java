package com.aninfo.api.validation;

import com.aninfo.exceptions.RequestValidationException;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isAlpha;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class Validator {

    private static final int ALIAS_MAX_CHARACTERS = 10;

    private static final String CBU_VALIDATION_MESSAGE = "CBU could not be null or lower than zero.";
    private static final String DESTINATION_CBU_VALIDATION_MESSAGE = "Destination CBU could not be null or lower than zero.";
    private static final String SUM_VALIDATION_MESSAGE = "The SUM could not be null or lower than zero.";
    private static final String TRANSACTION_TYPE_VALIDATION_MESSAGE = "Transaction type could not be null nor empty.";
    private static final String CURRENCY_VALIDATION_MESSAGE = "The currency could not be null nor empty.";
    private static final String ACCOUNT_NAME_VALIDATION_MESSAGE = "The account name type could not be null nor empty.";
    private static final String ACCOUNT_ALIAS_EMPTY_VALIDATION_MESSAGE = "The account alias should not be null nor empty.";
    private static final String ACCOUNT_ALIAS_EXCEEDED_VALIDATION_MESSAGE = "The account alias could not exceed 10 characters.";
    private static final String ACCOUNT_ALIAS_INVALID_VALIDATION_MESSAGE = "The account alias should only have alphabetic characters.";

    public void validateCbu(Long cbu) {
        if(!isValid(cbu)) {
            throw new RequestValidationException(CBU_VALIDATION_MESSAGE);
        }
    }

    public void validateDestinationCbu(Long destinationCbu) {
        if(!isValid(destinationCbu)) {
            throw new RequestValidationException(DESTINATION_CBU_VALIDATION_MESSAGE);
        }
    }

    public void validateSum(Double sum) {
        if(sum == null || sum <= 0) {
            throw new RequestValidationException(SUM_VALIDATION_MESSAGE);
        }
    }

    public void validateTransactionType(String transactionType) {
        if(isBlank(transactionType)) {
            throw new RequestValidationException(TRANSACTION_TYPE_VALIDATION_MESSAGE);
        }
    }

    public void validateNotEquals(Long l1, Long l2, String message) {
        if(l1.equals(l2)) {
            throw new RequestValidationException(message);
        }
    }

    public void validateCurrency(String currency) {
        if(isBlank(currency)) {
            throw new RequestValidationException(CURRENCY_VALIDATION_MESSAGE);
        }
    }

    public void validateName(String name) {
        if(isBlank(name)) {
            throw new RequestValidationException(ACCOUNT_NAME_VALIDATION_MESSAGE);
        }
    }

    public void validateAlias(String alias) {
        if(isBlank(alias)) {
            throw new RequestValidationException(ACCOUNT_ALIAS_EMPTY_VALIDATION_MESSAGE);
        }

        if(alias.length() > ALIAS_MAX_CHARACTERS) {
            throw new RequestValidationException(ACCOUNT_ALIAS_EXCEEDED_VALIDATION_MESSAGE);
        }

        if(!isAlpha(alias)) {
            throw new RequestValidationException(ACCOUNT_ALIAS_INVALID_VALIDATION_MESSAGE);
        }
    }

    private boolean isValid(Long cbu) {
        return cbu != null && cbu > 0;
    }
}
