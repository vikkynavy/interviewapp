package com.vijay.interviewapp.controller;

import com.vijay.interviewapp.common.ApiResponse;
import com.vijay.interviewapp.dto.CreateUserRequest;
import com.vijay.interviewapp.dto.UserResponseDTO;
import com.vijay.interviewapp.entity.User;
import com.vijay.interviewapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;

    }

    @GetMapping
    public List<UserResponseDTO> getUsers() {
        return service.getAllUsers().
                stream()
                .map(user -> new UserResponseDTO(
                      user.getId(),
                        user.getEmail(),
                        user.getRole()
                )).toList();

    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = service.createUser(request);

        ApiResponse<User> response = new ApiResponse<>( true,
                "User created successfully",
                user,
                Collections.emptyMap());
        response.setSuccess(true);
        response.setMessage("User created successfully");
        response.setData(user);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return service.updateUser(id, user);
    }


}
