package com.fishinginstreams.advice;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger();

    @Before("within(com.fishinginstreams.*)")
    public void logBefore(JoinPoint jp) {
        logger.log(Level.ALL, "Running method: " + jp.toString());
    }

    @After("within(com.fishinginstreams.*)")
    public void logAfter(JoinPoint jp) {
        logger.log(Level.ALL, "Finished running: " + jp.toString());
    }

    @AfterThrowing(pointcut = "within(com.fishinginstreams.*)", throwing = "ex")
    public void logAfterThrowing(JoinPoint jp, Exception ex) {
        logger.error("Exception: " + ex.getMessage() + " thrown by: " + jp.toString());
    }
}
