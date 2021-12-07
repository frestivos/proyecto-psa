package com.aninfo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class AlreadyExistsExeption extends RuntimeException {

    public AlreadyExistsExeption(String message) {
        super(message);
    }
}
