package com.fishinginstreams.exception;

public class UniqueConstraintViolationException extends RuntimeException {

    public UniqueConstraintViolationException(String column, String value) {
        super("'"+column+"' value: "+value+" already exists in the database and it cannot be duplicated");
    }
}
