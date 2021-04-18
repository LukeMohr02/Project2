package com.fishinginstreams.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotNullConstraintViolationException extends RuntimeException {

    public NotNullConstraintViolationException(String column) {
        super("'"+column+"' value(s) cannot be null or 0");
    }
}
