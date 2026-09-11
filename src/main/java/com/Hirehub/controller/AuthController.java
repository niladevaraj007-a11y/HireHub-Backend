package com.Hirehub.controller;

import com.Hirehub.dto.LoginRequest;
import com.Hirehub.dto.RegisterRequest;
import com.Hirehub.dto.UserResponse;
import com.Hirehub.entity.JobSeeker;
import com.Hirehub.entity.User;
import com.Hirehub.repository.JobSeekerRepository;
import com.Hirehub.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class AuthController {

    private final AuthService authService;
    private final JobSeekerRepository jobSeekerRepository;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AuthController(
            AuthService authService,
            JobSeekerRepository jobSeekerRepository) {

        this.authService = authService;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    // =========================================================
    // LOGIN
    // =========================================================

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(
            @RequestBody LoginRequest request) {

        User user = authService.login(request);

        JobSeeker jobSeeker = null;

        // Get job seeker profile
        if (user.getRole() == User.Role.JOB_SEEKER) {

            jobSeeker = jobSeekerRepository
                    .findByUserId(user.getUserId())
                    .orElse(null);
        }

        // Debug
        System.out.println("=================================");
        System.out.println("LOGIN SUCCESS");
        System.out.println("User ID   : " + user.getUserId());
        System.out.println("Email     : " + user.getEmail());
        System.out.println("Role      : " + user.getRole());

        if (jobSeeker != null) {
            System.out.println(
                    "Seeker ID : " + jobSeeker.getSeekerId()
            );
        } else {
            System.out.println("Seeker ID : NULL");
        }

        System.out.println("=================================");

        return ResponseEntity.ok(
                new UserResponse(user, jobSeeker)
        );
    }

    // =========================================================
    // REGISTER
    // =========================================================

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        JobSeeker jobSeeker = null;

        if (user.getRole() == User.Role.JOB_SEEKER) {

            jobSeeker = jobSeekerRepository
                    .findByUserId(user.getUserId())
                    .orElse(null);
        }

        return ResponseEntity.ok(
                new UserResponse(user, jobSeeker)
        );
    }
}