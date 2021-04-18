package com.fishinginstreams.exception;

import com.fishinginstreams.model.Angler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoAnglerFoundByUsername extends EntityNotFoundException {
    public NoAnglerFoundByUsername(String username) {
        super("No entry found with username: " + username);
    }
}
