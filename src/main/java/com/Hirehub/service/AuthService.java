package com.Hirehub.service;

import com.Hirehub.dto.LoginRequest;
import com.Hirehub.dto.RegisterRequest;
import com.Hirehub.entity.JobSeeker;
import com.Hirehub.entity.User;
import com.Hirehub.repository.JobSeekerRepository;
import com.Hirehub.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AuthService(
            UserRepository userRepository,
            JobSeekerRepository jobSeekerRepository) {

        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;

        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // =========================================================
    // REGISTER
    // =========================================================

    @Transactional
    public User register(RegisterRequest request) {

        // Check email
        if (userRepository
                .findByEmail(request.getEmail())
                .isPresent()) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }

        // Create User
        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        // Encrypt password
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());

        // Save User first
        User savedUser = userRepository.save(user);

        System.out.println(
                "Created User ID: "
                        + savedUser.getUserId()
        );

        // =====================================================
        // CREATE JOB SEEKER PROFILE
        // =====================================================

        if (savedUser.getRole() == User.Role.JOB_SEEKER) {

            JobSeeker jobSeeker = new JobSeeker();

            jobSeeker.setUserId(
                    savedUser.getUserId()
            );

            jobSeeker.setHeadline("Fresher");

            jobSeeker.setAbout(
                    "Fresher looking for job opportunities."
            );

            jobSeeker.setEducation(
                    "Not specified"
            );

            jobSeeker.setExperienceYears(
                    BigDecimal.ZERO
            );

            jobSeeker.setLocation(
                    "Not specified"
            );

            JobSeeker savedSeeker =
                    jobSeekerRepository.save(jobSeeker);

            System.out.println(
                    "Created Seeker ID: "
                            + savedSeeker.getSeekerId()
            );
        }

        return savedUser;
    }

    // =========================================================
    // LOGIN
    // =========================================================

    public User login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid email or password"
                        )
                );

        // Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        return user;
    }
}