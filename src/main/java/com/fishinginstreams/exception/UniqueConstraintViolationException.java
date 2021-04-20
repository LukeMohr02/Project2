package com.fishinginstreams.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UniqueConstraintViolationException extends RuntimeException {

    public UniqueConstraintViolationException(String column, String value) {
        super("'"+column+"' value: "+value+" already exists in the database and it cannot be duplicated");
    }
}
