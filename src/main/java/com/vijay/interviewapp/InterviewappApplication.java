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

@SpringBootApplication
public class InterviewappApplication {


    public static void main(String[] args) {

        System.out.println("SPRING_DATASOURCE_URL=" + System.getenv("SPRING_DATASOURCE_URL"));
        System.out.println("DATABASE_URL=" + System.getenv("DATABASE_URL"));

        System.out.println("BUILD CHECK: " + System.currentTimeMillis());

        SpringApplication.run(InterviewappApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(UserRepository repo) {
        return args -> {
            System.out.println("RUNNER STARTED"); // add this

            PasswordEncoder encoder = new BCryptPasswordEncoder();

            User user = new User();

            user.setEmail("test@example.com");
            user.setPassword(encoder.encode("password123"));
            user.setRole(Role.USER);

            repo.save(user);

            System.out.println("FORCED USER CREATED");
        };
    }
}
