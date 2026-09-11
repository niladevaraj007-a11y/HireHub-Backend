package com.Hirehub.service;

import com.Hirehub.entity.JobSeeker;
import com.Hirehub.repository.JobSeekerRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;

    public JobSeekerService(
            JobSeekerRepository jobSeekerRepository) {

        this.jobSeekerRepository =
                jobSeekerRepository;
    }

    // =========================================
    // CREATE JOB SEEKER PROFILE
    // =========================================

    public JobSeeker createProfile(
            JobSeeker jobSeeker) {

        if (jobSeeker.getUserId() == null) {

            throw new RuntimeException(
                    "User ID is required");
        }

        if (jobSeekerRepository
                .findByUserId(jobSeeker.getUserId())
                .isPresent()) {

            throw new RuntimeException(
                    "Job seeker profile already exists");
        }

        return jobSeekerRepository.save(
                jobSeeker);
    }

    // =========================================
    // GET ALL JOB SEEKER PROFILES
    // =========================================

    public List<JobSeeker> getAllProfiles() {

        return jobSeekerRepository.findAll();
    }

    // =========================================
    // GET PROFILE BY SEEKER ID
    // =========================================

    public JobSeeker getProfileById(
            Integer seekerId) {

        return jobSeekerRepository
                .findById(seekerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job seeker profile not found"));
    }

    // =========================================
    // GET PROFILE BY USER ID
    // =========================================

    public JobSeeker getProfileByUserId(
            Integer userId) {

        return jobSeekerRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job seeker profile not found"));
    }

    // =========================================
    // UPDATE PROFILE
    // =========================================

    public JobSeeker updateProfile(
            Integer seekerId,
            JobSeeker updatedProfile) {

        JobSeeker existingProfile =
                jobSeekerRepository
                        .findById(seekerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job seeker profile not found"));

        if (updatedProfile.getHeadline() != null) {

            existingProfile.setHeadline(
                    updatedProfile.getHeadline());
        }

        if (updatedProfile.getLocation() != null) {

            existingProfile.setLocation(
                    updatedProfile.getLocation());
        }

        if (updatedProfile.getExperienceYears()
                != null) {

            existingProfile.setExperienceYears(
                    updatedProfile.getExperienceYears());
        }

        if (updatedProfile.getEducation() != null) {

            existingProfile.setEducation(
                    updatedProfile.getEducation());
        }

        if (updatedProfile.getAbout() != null) {

            existingProfile.setAbout(
                    updatedProfile.getAbout());
        }

        return jobSeekerRepository.save(
                existingProfile);
    }

    // =========================================
    // DELETE PROFILE
    // =========================================

    public void deleteProfile(
            Integer seekerId) {

        if (!jobSeekerRepository
                .existsById(seekerId)) {

            throw new RuntimeException(
                    "Job seeker profile not found");
        }

        jobSeekerRepository.deleteById(
                seekerId);
    }
}