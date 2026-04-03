package com.vijay.interviewapp.config;

import com.vijay.interviewapp.entity.Role;
import com.vijay.interviewapp.entity.User;
import com.vijay.interviewapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;



    @Override
    public void run(String... args) {

        // check if user already exists

        if (repo.findByEmail("test@example.com").isEmpty()) {

            User user = new User();
            user.setEmail("test@example.com");
            user.setPassword(encoder.encode("password123"));
            user.setRole(Role.USER);

            repo.save(user);

            System.out.println("✅ Test user created");
        }

    }
}
