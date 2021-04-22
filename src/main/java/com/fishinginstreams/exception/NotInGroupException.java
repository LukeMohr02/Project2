package com.fishinginstreams.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotInGroupException extends RuntimeException {

    public NotInGroupException(String username, String group) {
        super("Angler '" + username + "' cannot leave group '" + group +"' because they are not in it");
    }
}
