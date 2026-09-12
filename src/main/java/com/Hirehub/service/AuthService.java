package com.Hirehub.service;

import com.Hirehub.dto.LoginRequest;
import com.Hirehub.dto.RegisterRequest;
import com.Hirehub.entity.JobSeeker;
import com.Hirehub.entity.Recruiter;
import com.Hirehub.entity.User;
import com.Hirehub.repository.JobSeekerRepository;
import com.Hirehub.repository.RecruiterRepository;
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

    private final RecruiterRepository recruiterRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            JobSeekerRepository jobSeekerRepository,
            RecruiterRepository recruiterRepository) {

        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.recruiterRepository = recruiterRepository;

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

        // Check role
        if (request.getRole() == null) {

            throw new RuntimeException(
                    "Account type is required"
            );
        }

        // =====================================================
        // CREATE USER
        // =====================================================

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        System.out.println(
                "Created User ID: "
                        + savedUser.getUserId()
        );

        // =====================================================
        // JOB SEEKER
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

        // =====================================================
        // RECRUITER
        // =====================================================

        if (savedUser.getRole() == User.Role.RECRUITER) {

            Recruiter recruiter = new Recruiter();

            recruiter.setUserId(
                    savedUser.getUserId()
            );

            recruiter.setDesignation(
                    "HR Recruiter"
            );

            /*
             * Company will be created later from
             * the Create Company page.
             *
             * Therefore companyId is initially null.
             */
            recruiter.setCompanyId(null);

            Recruiter savedRecruiter =
                    recruiterRepository.save(recruiter);

            System.out.println(
                    "Created Recruiter ID: "
                            + savedRecruiter.getRecruiterId()
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