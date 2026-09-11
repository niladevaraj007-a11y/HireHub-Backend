package com.Hirehub.controller;

import com.Hirehub.entity.SavedJob;
import com.Hirehub.service.SavedJobService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-jobs")
@CrossOrigin
public class SavedJobController {

    private final SavedJobService savedJobService;

    public SavedJobController(SavedJobService savedJobService) {
        this.savedJobService = savedJobService;
    }

    // =========================================================
    // SAVE JOB
    // POST /api/saved-jobs/save
    // =========================================================

    @PostMapping("/save")
    public ResponseEntity<SavedJob> saveJob(
            @RequestParam Integer seekerId,
            @RequestParam Integer jobId) {

        return ResponseEntity.ok(
                savedJobService.saveJob(seekerId, jobId)
        );
    }

    // =========================================================
    // GET SAVED JOBS OF SEEKER
    // GET /api/saved-jobs/seeker/{seekerId}
    // =========================================================

    @GetMapping("/seeker/{seekerId}")
    public ResponseEntity<List<SavedJob>> getSavedJobs(
            @PathVariable Integer seekerId) {

        return ResponseEntity.ok(
                savedJobService.getSavedJobs(seekerId)
        );
    }

    // =========================================================
    // REMOVE SAVED JOB
    // DELETE /api/saved-jobs/remove
    // =========================================================

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeSavedJob(
            @RequestParam Integer seekerId,
            @RequestParam Integer jobId) {

        savedJobService.removeSavedJob(
                seekerId,
                jobId
        );

        return ResponseEntity.ok(
                "Job removed from saved jobs"
        );
    }
}