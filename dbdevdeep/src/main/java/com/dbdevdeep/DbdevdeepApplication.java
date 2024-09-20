package com.dbdevdeep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DbdevdeepApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbdevdeepApplication.class, args);
	}

}
