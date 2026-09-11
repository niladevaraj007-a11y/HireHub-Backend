package com.Hirehub.controller;

import com.Hirehub.dto.ApplicantResponse;
import com.Hirehub.dto.ApplicationResponse;
import com.Hirehub.entity.Application;
import com.Hirehub.service.ApplicantService;
import com.Hirehub.service.ApplicationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicantService applicantService;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ApplicationController(
            ApplicationService applicationService,
            ApplicantService applicantService) {

        this.applicationService = applicationService;
        this.applicantService = applicantService;
    }

    // =========================================================
    // GET ALL APPLICATIONS
    // GET /api/applications
    // =========================================================

    @GetMapping
    public List<Application> getAllApplications() {

        return applicationService.getAllApplications();
    }

    // =========================================================
    // APPLY FOR A JOB
    // POST /api/applications/apply
    //
    // Example:
    // /api/applications/apply?jobId=2&seekerId=6&resumeId=8
    // =========================================================

    @PostMapping("/apply")
    public Application applyForJob(
            @RequestParam Integer jobId,
            @RequestParam Integer seekerId,
            @RequestParam Integer resumeId) {

        return applicationService.applyForJob(
                jobId,
                seekerId,
                resumeId
        );
    }

    // =========================================================
    // GET APPLICATIONS BY SEEKER
    // GET /api/applications/seeker/{seekerId}
    // =========================================================

    @GetMapping("/seeker/{seekerId}")
    public List<ApplicationResponse> getApplicationsBySeeker(
            @PathVariable Integer seekerId) {

        return applicationService.getApplicationsBySeeker(seekerId);
    }

    // =========================================================
    // GET APPLICATIONS BY JOB
    // GET /api/applications/job/{jobId}
    //
    // Used by RecruiterApplicants.jsx
    // =========================================================

    @GetMapping("/job/{jobId}")
    public List<ApplicantResponse> getApplicationsByJob(
            @PathVariable Integer jobId) {

        List<Application> applications =
                applicationService.getApplicationsByJob(jobId);

        return applications.stream()
                .map(application ->
                        applicantService.getApplicantByApplicationId(
                                application.getApplicationId()
                        )
                )
                .toList();
    }

    // =========================================================
    // GET APPLICATION BY ID
    // GET /api/applications/{applicationId}
    // =========================================================

    @GetMapping("/{applicationId}")
    public Application getApplicationById(
            @PathVariable Integer applicationId) {

        return applicationService.getApplicationById(applicationId);
    }

    // =========================================================
    // UPDATE APPLICATION STATUS
    // PUT /api/applications/{applicationId}/status
    //
    // Example:
    // /api/applications/15/status?status=SHORTLISTED
    //
    // Allowed:
    // APPLIED
    // SHORTLISTED
    // SELECTED
    // REJECTED
    // =========================================================

    @PutMapping("/{applicationId}/status")
    public Application updateApplicationStatus(
            @PathVariable Integer applicationId,
            @RequestParam String status) {

        return applicationService.updateApplicationStatus(
                applicationId,
                status
        );
    }

    // =========================================================
    // GET APPLICANT DETAILS
    // GET /api/applications/{applicationId}/applicant
    //
    // Example:
    // /api/applications/15/applicant
    // =========================================================

    @GetMapping("/{applicationId}/applicant")
    public ApplicantResponse getApplicant(
            @PathVariable Integer applicationId) {

        return applicantService.getApplicantByApplicationId(
                applicationId
        );
    }
}