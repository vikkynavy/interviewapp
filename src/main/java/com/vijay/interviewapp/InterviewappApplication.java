package com.vijay.interviewapp;

import com.vijay.interviewapp.entity.Role;
import com.vijay.interviewapp.entity.User;
import com.vijay.interviewapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.TimeZone;

@SpringBootApplication
public class InterviewappApplication {


    public static void main(String[] args) {

        System.out.println("SPRING_DATASOURCE_URL=" + System.getenv("SPRING_DATASOURCE_URL"));
        System.out.println("DATABASE_URL=" + System.getenv("DATABASE_URL"));

        System.out.println("BUILD CHECK: " + System.currentTimeMillis());

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        SpringApplication.run(InterviewappApplication.class, args);
    }
}
