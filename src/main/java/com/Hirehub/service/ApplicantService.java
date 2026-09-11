package com.Hirehub.service;

import com.Hirehub.dto.ApplicantResponse;
import com.Hirehub.entity.Application;
import com.Hirehub.entity.JobSeeker;
import com.Hirehub.entity.Resume;
import com.Hirehub.entity.User;
import com.Hirehub.repository.ApplicationRepository;
import com.Hirehub.repository.JobSeekerRepository;
import com.Hirehub.repository.ResumeRepository;
import com.Hirehub.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class ApplicantService {

    private final ApplicationRepository applicationRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ApplicantService(
            ApplicationRepository applicationRepository,
            JobSeekerRepository jobSeekerRepository,
            ResumeRepository resumeRepository,
            UserRepository userRepository) {

        this.applicationRepository = applicationRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    // =========================================================
    // GET APPLICANT DETAILS BY APPLICATION ID
    // =========================================================

    public ApplicantResponse getApplicantByApplicationId(
            Integer applicationId) {

        // -----------------------------------------------------
        // 1. Find application
        // -----------------------------------------------------

        Application application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"));

        // -----------------------------------------------------
        // 2. Find job seeker
        // -----------------------------------------------------

        JobSeeker jobSeeker =
                jobSeekerRepository.findById(
                        application.getSeekerId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job seeker not found"));

        // -----------------------------------------------------
        // 3. Find user
        // -----------------------------------------------------

        User user =
                userRepository.findById(
                        jobSeeker.getUserId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        // -----------------------------------------------------
        // 4. Find resume
        // -----------------------------------------------------

        Resume resume = null;

        if (application.getResumeId() != null) {

            resume =
                    resumeRepository.findById(
                            application.getResumeId())
                            .orElse(null);
        }

        // -----------------------------------------------------
        // 5. Create ApplicantResponse
        // -----------------------------------------------------

        ApplicantResponse response =
                new ApplicantResponse();

        // Application details
        response.setApplicationId(
                application.getApplicationId());

        response.setAppliedAt(
                application.getAppliedAt() != null
                        ? application.getAppliedAt().toString()
                        : null);

        response.setStatus(
                application.getStatus() != null
                        ? application.getStatus().name()
                        : "APPLIED");

        // Job seeker details
        response.setSeekerId(
                jobSeeker.getSeekerId());

        response.setHeadline(
                jobSeeker.getHeadline());

        response.setLocation(
                jobSeeker.getLocation());

        response.setExperienceYears(
                jobSeeker.getExperienceYears());

        response.setEducation(
                jobSeeker.getEducation());

        response.setAbout(
                jobSeeker.getAbout());

        // User details
        response.setUserId(
                user.getUserId());

        response.setFullName(
                user.getFullName());

        response.setEmail(
                user.getEmail());

        response.setPhone(
                user.getPhone());

        // Resume details
        response.setResumeId(
                application.getResumeId());

        if (resume != null) {

            response.setResumeFileName(
                    resume.getFileName());
        } else {

            response.setResumeFileName(null);
        }

        return response;
    }
}