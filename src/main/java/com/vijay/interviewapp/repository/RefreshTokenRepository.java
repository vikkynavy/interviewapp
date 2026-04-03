package com.vijay.interviewapp.repository;

import com.vijay.interviewapp.entity.RefreshToken;
import com.vijay.interviewapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
