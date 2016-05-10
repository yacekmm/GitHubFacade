package com.looksok.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.looksok")
public class GitHubFacadeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitHubFacadeServiceApplication.class, args);
	}
}
