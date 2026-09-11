package com.Hirehub.dto;

import com.Hirehub.entity.JobSeeker;
import com.Hirehub.entity.User;

public class UserResponse {

    // =========================================================
    // FIELDS
    // =========================================================

    private Integer userId;

    private Integer seekerId;

    private String fullName;

    private String email;

    private String phone;

    private User.Role role;

    // =========================================================
    // CONSTRUCTOR - USER ONLY
    // =========================================================

    public UserResponse(User user) {
        this(user, null);
    }

    // =========================================================
    // CONSTRUCTOR - USER + JOB SEEKER
    // =========================================================

    public UserResponse(User user, JobSeeker jobSeeker) {

        if (user == null) {
            return;
        }

        // User information
        this.userId = user.getUserId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole();

        // Job seeker information
        if (jobSeeker != null) {
            this.seekerId = jobSeeker.getSeekerId();
        }
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public Integer getUserId() {
        return userId;
    }

    public Integer getSeekerId() {
        return seekerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public User.Role getRole() {
        return role;
    }

    // =========================================================
    // SETTERS
    // =========================================================

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setSeekerId(Integer seekerId) {
        this.seekerId = seekerId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}