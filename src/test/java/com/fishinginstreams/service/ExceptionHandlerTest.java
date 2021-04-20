package com.fishinginstreams.service;

import com.fishinginstreams.exception.NotNullConstraintViolationException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionHandlerTest {

//    @InjectMocks
    ExceptionHandler exceptionHandler;

    Integer[] nullTestInteger;
    Object[] nullTestObject;
    String[] nullTestString;
    String[] columns;

    @BeforeEach
    public void setup(){
        exceptionHandler = new ExceptionHandler();
        nullTestInteger = new Integer[]{0};
        columns = new String[]{"Test Column"};
        nullTestString = new String[]{""};
        nullTestObject = new Object[]{null};
    }

    @Test
    public void testNotNullConstraintViolationException(){
        assertThrows(NotNullConstraintViolationException.class, () -> exceptionHandler.NotNullConstraintViolation(nullTestString,columns));
    }

    @Test
    public void testNotNullConstraintViolation2Exception(){
        assertThrows(NotNullConstraintViolationException.class, () -> exceptionHandler.NotNullConstraintViolation(nullTestInteger,columns));
    }

    @Test
    public void testEntityNotFoundException(){
        assertThrows(EntityNotFoundException.class, () -> exceptionHandler.EntityNotFound(nullTestObject,columns));
    }

}