package com.fishinginstreams;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.Entity;

@SpringBootApplication
public class Project2Application {
	static Logger logger = LogManager.getLogger(Project2Application.class.getName());

	public static void main(String[] args) {

		SpringApplication.run(Project2Application.class, args);

	}


}
