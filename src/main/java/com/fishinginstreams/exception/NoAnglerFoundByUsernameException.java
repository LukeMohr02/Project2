package com.fishinginstreams.exception;

import com.fishinginstreams.model.Angler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoAnglerFoundByUsernameException extends EntityNotFoundException {
    public NoAnglerFoundByUsernameException(String username) {
        super("No entry found with username: " + username);
    }
}
