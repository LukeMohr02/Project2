package com.fishinginstreams.exception;

public class NotNullConstraintViolationException extends RuntimeException {

    public NotNullConstraintViolationException(String column) {
        super("'"+column+"' value(s) cannot be null or 0");
    }
}
