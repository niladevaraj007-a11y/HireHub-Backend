package com.Hirehub.controller;

import com.Hirehub.entity.JobSeeker;
import com.Hirehub.service.JobSeekerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-seekers")
@CrossOrigin
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;

    public JobSeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    // =========================================
    // CREATE PROFILE
    // POST /api/job-seekers
    // =========================================

    @PostMapping
    public ResponseEntity<JobSeeker> createProfile(
            @RequestBody JobSeeker jobSeeker) {

        return ResponseEntity.ok(
                jobSeekerService.createProfile(jobSeeker)
        );
    }

    // =========================================
    // GET ALL PROFILES
    // GET /api/job-seekers
    // =========================================

    @GetMapping
    public ResponseEntity<List<JobSeeker>> getAllProfiles() {

        return ResponseEntity.ok(
                jobSeekerService.getAllProfiles()
        );
    }

    // =========================================
    // GET PROFILE BY SEEKER ID
    // GET /api/job-seekers/{seekerId}
    // =========================================

    @GetMapping("/{seekerId}")
    public ResponseEntity<JobSeeker> getProfileById(
            @PathVariable Integer seekerId) {

        return ResponseEntity.ok(
                jobSeekerService.getProfileById(seekerId)
        );
    }

    // =========================================
    // GET PROFILE BY USER ID
    // GET /api/job-seekers/user/{userId}
    // =========================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<JobSeeker> getProfileByUserId(
            @PathVariable Integer userId) {

        return ResponseEntity.ok(
                jobSeekerService.getProfileByUserId(userId)
        );
    }

    // =========================================
    // UPDATE PROFILE
    // PUT /api/job-seekers/{seekerId}
    // =========================================

    @PutMapping("/{seekerId}")
    public ResponseEntity<JobSeeker> updateProfile(
            @PathVariable Integer seekerId,
            @RequestBody JobSeeker jobSeeker) {

        return ResponseEntity.ok(
                jobSeekerService.updateProfile(
                        seekerId,
                        jobSeeker
                )
        );
    }

    // =========================================
    // DELETE PROFILE
    // DELETE /api/job-seekers/{seekerId}
    // =========================================

    @DeleteMapping("/{seekerId}")
    public ResponseEntity<String> deleteProfile(
            @PathVariable Integer seekerId) {

        jobSeekerService.deleteProfile(seekerId);

        return ResponseEntity.ok(
                "Job seeker profile deleted successfully"
        );
    }
}