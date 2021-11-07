package com.aninfo.transformer;

import com.aninfo.api.request.TransactionRequest;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.EnumUtils.isValidEnum;

@Component
public class TransactionTransformer {

    public Transaction transform(Long cbu, TransactionRequest transactionRequest) {
        String type = transactionRequest.getType();
        if(!isValidEnum(TransactionType.class, type)) {
            throw new InvalidTransactionTypeException("Invalid transaction type.");
        }

        return new Transaction(cbu,
                transactionRequest.getDestinationCbu(),
                TransactionType.valueOf(type),
                transactionRequest.getSum());
    }
}
