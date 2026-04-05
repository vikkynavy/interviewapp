package com.vijay.interviewapp.controller;

import com.vijay.interviewapp.common.ApiResponse;
import com.vijay.interviewapp.dto.CreateUserRequest;
import com.vijay.interviewapp.dto.UpdateUserRequest;
import com.vijay.interviewapp.dto.UserResponseDTO;
import com.vijay.interviewapp.entity.User;
import com.vijay.interviewapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;

    }

    @GetMapping
    public List<UserResponseDTO> getUsers() {
        return service.getAllUsers();

    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = service.createUser(request);

        UserResponseDTO dto = new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        ApiResponse<UserResponseDTO> response = new ApiResponse<>( true, "User created successfully", dto, Collections.emptyMap());
       /* response.setSuccess(true);
        response.setMessage("User created successfully");
        response.setData(dto);*/

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return service.updateUser(id, request);
    }


}
