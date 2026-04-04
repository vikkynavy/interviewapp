package com.vijay.interviewapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InterviewappApplication {

	public static void main(String[] args) {

        System.out.println("SPRING_DATASOURCE_URL=" + System.getenv("SPRING_DATASOURCE_URL"));
        System.out.println("DATABASE_URL=" + System.getenv("DATABASE_URL"));


		SpringApplication.run(InterviewappApplication.class, args);
	}

}
