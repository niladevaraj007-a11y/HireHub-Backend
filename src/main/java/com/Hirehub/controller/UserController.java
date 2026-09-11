package com.Hirehub.controller;

import com.Hirehub.entity.User;
import com.Hirehub.dto.UserResponse;
import com.Hirehub.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // =========================================================
    // GET ALL USERS
    // GET /api/users
    // =========================================================

    @GetMapping
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    // =========================================================
    // GET USER BY ID
    // GET /api/users/{userId}
    // =========================================================

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return ResponseEntity.ok(
                new UserResponse(user)
        );
    }

    // =========================================================
    // UPDATE USER PROFILE
    // PUT /api/users/{userId}
    // =========================================================

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Integer userId,
            @RequestBody User updatedUser) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // -----------------------------------------------------
        // UPDATE PROFILE FIELDS
        // -----------------------------------------------------

        if (updatedUser.getFullName() != null) {
            existingUser.setFullName(updatedUser.getFullName());
        }

        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPhone() != null) {
            existingUser.setPhone(updatedUser.getPhone());
        }

        if (updatedUser.getLocation() != null) {
            existingUser.setLocation(updatedUser.getLocation());
        }

        if (updatedUser.getSkills() != null) {
            existingUser.setSkills(updatedUser.getSkills());
        }

        if (updatedUser.getBio() != null) {
            existingUser.setBio(updatedUser.getBio());
        }

        // -----------------------------------------------------
        // SAVE
        // -----------------------------------------------------

        User savedUser = userRepository.save(existingUser);

        return ResponseEntity.ok(
                new UserResponse(savedUser)
        );
    }
}