package com.fishinginstreams.exception;

public class AlreadyInGroupException extends RuntimeException {

    public AlreadyInGroupException(String username, String group) {
        super("Angler '" + username + "' has already joined group '" + group +"'");
    }
}
