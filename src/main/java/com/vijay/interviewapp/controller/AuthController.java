package com.vijay.interviewapp.controller;

import com.vijay.interviewapp.dto.LoginRequest;
import com.vijay.interviewapp.dto.LoginResponse;
import com.vijay.interviewapp.dto.RefreshRequest;
import com.vijay.interviewapp.entity.RefreshToken;
import com.vijay.interviewapp.entity.User;
import com.vijay.interviewapp.repository.RefreshTokenRepository;
import com.vijay.interviewapp.repository.UserRepository;
import com.vijay.interviewapp.service.RefreshTokenService;
import com.vijay.interviewapp.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {

        System.out.println("LOGIN HIT: " + req.getEmail());

        User user = userRepository.findByEmail(req.getEmail())
                .orElse(null);

        System.out.println("USER ROLE: " + user.getRole());

        if (user == null) {
            System.out.println("❌ USER NOT FOUND");
        } else {
            System.out.println("✅ USER FOUND: " + user.getEmail());
            System.out.println("DB PASSWORD: " + user.getPassword());

            System.out.println("INPUT PASSWORD: " + req.getPassword());
            System.out.println("MATCH: " + encoder.matches(req.getPassword(), user.getPassword()));
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getPassword()
                    )
            );
            System.out.println("✅ AUTH SUCCESS");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT expired");
        } catch (Exception e) {
            System.out.println("JWT error: " + e.getMessage());
        }



        String token = jwtUtil.generateToken(user);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user.getEmail());

        return new LoginResponse(
                token,
                user.getEmail(),
                user.getRole().name(),
                refreshToken.getToken()
        );



    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String refreshToken) {

        refreshTokenService.findByToken(refreshToken)
                .ifPresent(token -> refreshTokenService.deleteByUser(token.getUser()));

        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/encode")
    public String encode() {
        return encoder.encode("admin");
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {

        System.out.println("REFRESH API HIT");

        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService
                .findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token"));

        User user = refreshToken.getUser();

        String newAccessToken = jwtUtil.generateToken(user);

        return ResponseEntity.ok(
                new LoginResponse(
                        newAccessToken,
                        user.getEmail(),
                        user.getRole().name(),
                        refreshToken.getToken()
                )
        );
    }

   /* @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> req) {

        String refreshToken = req.get("refreshToken");

       // RefreshToken token = refreshTokenService.verifyExpiration(refreshToken);

        RefreshToken token = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        token = refreshTokenService.verifyExpiration(token);

        User user = token.getUser();

        String newAccessToken = jwtUtil.generateToken(user);

        return ResponseEntity.ok(Map.of(
                "token", newAccessToken
        ));
    }*/

}
