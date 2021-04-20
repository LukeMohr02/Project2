package com.fishinginstreams.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectCredentialsException extends RuntimeException {

    public IncorrectCredentialsException(BadCredentialsException e) {
        super("Incorrect username or password, please try again", e);
    }
}
