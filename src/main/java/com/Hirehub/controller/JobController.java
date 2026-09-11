
package com.Hirehub.controller;

import com.Hirehub.entity.Job;
import com.Hirehub.service.JobService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // =========================================================
    // GET ALL JOBS
    // GET /api/jobs
    // =========================================================

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {

        return ResponseEntity.ok(
                jobService.getAllJobs()
        );
    }

    // =========================================================
    // GET JOB BY ID
    // GET /api/jobs/{id}
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                jobService.getJobById(id)
        );
    }

    // =========================================================
    // GET JOBS BY COMPANY
    // GET /api/jobs/company/{companyId}
    // =========================================================

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Job>> getJobsByCompany(
            @PathVariable Integer companyId) {

        return ResponseEntity.ok(
                jobService.getJobsByCompany(companyId)
        );
    }

    // =========================================================
    // CREATE JOB
    // POST /api/jobs?recruiterUserId=1
    // =========================================================

    @PostMapping
    public ResponseEntity<Job> createJob(
            @RequestParam Integer recruiterUserId,
            @RequestBody Job job) {

        return ResponseEntity.ok(
                jobService.createJob(
                        recruiterUserId,
                        job
                )
        );
    }

    // =========================================================
    // UPDATE JOB
    // PUT /api/jobs/{jobId}?recruiterUserId=1
    // =========================================================

    @PutMapping("/{jobId}")
    public ResponseEntity<Job> updateJob(
            @PathVariable Integer jobId,
            @RequestParam Integer recruiterUserId,
            @RequestBody Job job) {

        return ResponseEntity.ok(
                jobService.updateJob(
                        jobId,
                        recruiterUserId,
                        job
                )
        );
    }

    // =========================================================
    // DELETE JOB
    // DELETE /api/jobs/{jobId}?recruiterUserId=1
    // =========================================================

    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Integer jobId,
            @RequestParam Integer recruiterUserId) {

        jobService.deleteJob(
                jobId,
                recruiterUserId
        );

        return ResponseEntity.ok(
                "Job deleted successfully"
        );
    }
}

