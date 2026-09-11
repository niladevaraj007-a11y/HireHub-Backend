package com.Hirehub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    // =========================================================
    // PRIMARY KEY
    // =========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    // =========================================================
    // BASIC USER INFORMATION
    // =========================================================

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    // =========================================================
    // PROFILE INFORMATION
    // =========================================================

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "skills", length = 500)
    private String skills;

    @Column(name = "bio", length = 1000)
    private String bio;

    // =========================================================
    // CREATED DATE
    // =========================================================

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // =========================================================
    // ROLE
    // =========================================================

    public enum Role {
        JOB_SEEKER,
        RECRUITER,
        ADMIN
    }

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public User() {
    }

    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}