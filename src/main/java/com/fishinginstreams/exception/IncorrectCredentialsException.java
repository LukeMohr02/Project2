package com.fishinginstreams.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class IncorrectCredentialsException extends RuntimeException {

    public IncorrectCredentialsException(BadCredentialsException e) {
        super("Incorrect username or password, please try again", e);
    }
}
