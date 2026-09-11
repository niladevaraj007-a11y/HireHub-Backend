package com.Hirehub.service;

import com.Hirehub.entity.Recruiter;
import com.Hirehub.repository.RecruiterRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruiterService {

    private final RecruiterRepository recruiterRepository;

    public RecruiterService(
            RecruiterRepository recruiterRepository) {

        this.recruiterRepository = recruiterRepository;
    }

    // Create recruiter profile
    public Recruiter createRecruiter(Recruiter recruiter) {

        if (recruiter.getUserId() == null) {
            throw new RuntimeException(
                    "User ID is required");
        }

        if (recruiter.getCompanyId() == null) {
            throw new RuntimeException(
                    "Company ID is required");
        }

        // Prevent duplicate recruiter for same user
        if (recruiterRepository
                .findByUserId(recruiter.getUserId())
                .isPresent()) {

            throw new RuntimeException(
                    "Recruiter profile already exists for this user");
        }

        return recruiterRepository.save(recruiter);
    }

    // Get recruiter by ID
    public Recruiter getRecruiterById(
            Integer recruiterId) {

        return recruiterRepository.findById(recruiterId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter not found"));
    }

    // Get recruiter by user ID
    public Recruiter getRecruiterByUserId(
            Integer userId) {

        return recruiterRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter not found for this user"));
    }

    // Get all recruiters
    public List<Recruiter> getAllRecruiters() {

        return recruiterRepository.findAll();
    }

    // Update recruiter
    public Recruiter updateRecruiter(
            Integer recruiterId,
            Recruiter updatedRecruiter) {

        Recruiter recruiter =
                recruiterRepository.findById(recruiterId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter not found"));

        if (updatedRecruiter.getDesignation() != null) {
            recruiter.setDesignation(
                    updatedRecruiter.getDesignation());
        }

        if (updatedRecruiter.getCompanyId() != null) {
            recruiter.setCompanyId(
                    updatedRecruiter.getCompanyId());
        }

        return recruiterRepository.save(recruiter);
    }

    // Delete recruiter
    public String deleteRecruiter(
            Integer recruiterId) {

        Recruiter recruiter =
                recruiterRepository.findById(recruiterId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter not found"));

        recruiterRepository.delete(recruiter);

        return "Recruiter deleted successfully";
    }
}