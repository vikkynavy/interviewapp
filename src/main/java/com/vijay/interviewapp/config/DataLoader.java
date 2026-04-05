package com.vijay.interviewapp.config;

import com.vijay.interviewapp.entity.Role;
import com.vijay.interviewapp.entity.User;
import com.vijay.interviewapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;



    @Override
    public void run(String... args) {

        // check if user already exists

        // USER (existing)
        if (repo.findByEmail("test@example.com").isEmpty()) {
            User user = new User();
            user.setEmail("test@example.com");
            user.setPassword(encoder.encode("password123"));
            user.setRole(Role.USER);
            repo.save(user);
             System.out.println("✅ Test user created");
        }

// ADMIN (new)
        if (repo.findByEmail("admin@example.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            repo.save(admin);

            System.out.println("✅ admin user created");
        } else {
            System.out.println("⚠️ User already exists");
        }

    }
}
