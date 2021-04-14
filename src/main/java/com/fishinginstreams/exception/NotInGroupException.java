package com.fishinginstreams.exception;

public class NotInGroupException extends RuntimeException {

    public NotInGroupException(String username, String group) {
        super("Angler '" + username + "' cannot leave group '" + group +"' because they are not in it");
    }
}
