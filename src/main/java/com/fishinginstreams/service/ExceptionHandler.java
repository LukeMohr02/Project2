package com.fishinginstreams.service;

import com.fishinginstreams.exception.NotNullConstraintViolationException;

import javax.persistence.EntityNotFoundException;

public class ExceptionHandler {

    public void NotNullConstraintViolation(String[] testStrings, String[] columns) {
        for (int i = 0; i < testStrings.length; i++) {
            if (testStrings[i] == null || testStrings[i].equals("")) {
                throw new NotNullConstraintViolationException(columns[i]);
            }
        }
    }

    public void NotNullConstraintViolation(Integer[] testIntegers, String[] columns) {
        for (int i = 0; i < testIntegers.length; i++) {
            if (testIntegers[i] == null || testIntegers[i] == 0) {
                throw new NotNullConstraintViolationException(columns[i]);
            }
        }
    }

    public void EntityNotFound(Object[] testObjects, String[] columns) {
        for (int i = 0; i < testObjects.length; i++) {
            if (testObjects[i] == null) {
                throw new EntityNotFoundException("No entry found in column: " + columns[i]);
            }
        }
    }
}
