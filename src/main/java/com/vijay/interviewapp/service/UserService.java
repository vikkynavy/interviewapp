package com.vijay.interviewapp.service;

import com.vijay.interviewapp.dto.CreateUserRequest;
import com.vijay.interviewapp.dto.UserResponseDTO;
import com.vijay.interviewapp.exception.BusinessException;
import com.vijay.interviewapp.entity.Role;
import com.vijay.interviewapp.entity.User;
import com.vijay.interviewapp.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class UserService {

    /*private final List<User> users = new ArrayList<>();
    private Long idCounter = 1L;

    public User createUser(CreateUserRequest request) {

        User user = new User(
                idCounter++,
                request.getName(),
                request.getEmail()
        );

        users.add(user);

        return user;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User updateUser(Long id, CreateUserRequest request) {

        for (User user : users) {

            if (user.getId().equals(id)) {

                User updatedUser = new User(
                        id,
                        request.getName(),
                        request.getEmail()
                );

                users.remove(user);
                users.add(updatedUser);

                return updatedUser;
            }
        }

        return null;
    }

    public void deleteUser(Long id) {

        users.removeIf(user -> user.getId().equals(id));
    }*/

    private final UserRepository userRepository;

    public UserService(UserRepository repo) {
        this.userRepository = repo;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()   // ✅ missing piece
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getRole()   // ✅ FIXED (no .name())
                ))
                .toList();
    }

    public User createUser(CreateUserRequest request) {

        if(userRepository.existsByEmail(request.getEmail())) {

            throw new BusinessException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {

        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User user) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);
    }
}
