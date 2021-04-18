package com.fishinginstreams.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoFishFoundByIdException extends EntityNotFoundException {
    public NoFishFoundByIdException(int id){
        super("No entry found with fishId: " + id);
    }
}
