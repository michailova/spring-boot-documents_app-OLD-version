package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		}
)
public class SpringBootDocumentsApplication {

	public static void main(String[] args) {
		SpringApplication.

				run(SpringBootDocumentsApplication.class, args);
	}

}
