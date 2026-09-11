
package com.Hirehub.controller;

import com.Hirehub.entity.Interview;
import com.Hirehub.service.InterviewService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(
            InterviewService interviewService) {

        this.interviewService = interviewService;
    }

    // =========================================================
    // CREATE INTERVIEW
    // POST /api/interviews
    // =========================================================

    @PostMapping
    public Interview createInterview(
            @RequestBody Interview interview) {

        return interviewService
                .createInterview(interview);
    }

    // =========================================================
    // GET INTERVIEWS BY APPLICATION
    // GET /api/interviews/application/{applicationId}
    // =========================================================

    @GetMapping("/application/{applicationId}")
    public List<Interview> getInterviewsByApplication(
            @PathVariable Integer applicationId) {

        return interviewService
                .getInterviewsByApplication(applicationId);
    }

    // =========================================================
    // GET INTERVIEWS BY SEEKER
    // GET /api/interviews/seeker/{seekerId}
    // =========================================================

    @GetMapping("/seeker/{seekerId}")
    public List<Interview> getInterviewsBySeeker(
            @PathVariable Integer seekerId) {

        return interviewService
                .getInterviewsBySeeker(seekerId);
    }

    // =========================================================
    // GET INTERVIEW BY ID
    // GET /api/interviews/{interviewId}
    // =========================================================

    @GetMapping("/{interviewId}")
    public Interview getInterviewById(
            @PathVariable Integer interviewId) {

        return interviewService
                .getInterviewById(interviewId);
    }

    // =========================================================
    // UPDATE COMPLETE INTERVIEW
    // PUT /api/interviews/{interviewId}
    // =========================================================

    @PutMapping("/{interviewId}")
    public Interview updateInterview(
            @PathVariable Integer interviewId,
            @RequestBody Interview interview) {

        return interviewService
                .updateInterview(
                        interviewId,
                        interview);
    }

    // =========================================================
    // UPDATE INTERVIEW STATUS
    // PUT /api/interviews/{interviewId}/status
    // =========================================================

    @PutMapping("/{interviewId}/status")
    public Interview updateInterviewStatus(
            @PathVariable Integer interviewId,
            @RequestParam String status) {

        return interviewService
                .updateInterviewStatus(
                        interviewId,
                        status);
    }

    // =========================================================
    // DELETE INTERVIEW
    // DELETE /api/interviews/{interviewId}
    // =========================================================

    @DeleteMapping("/{interviewId}")
    public String deleteInterview(
            @PathVariable Integer interviewId) {

        interviewService
                .deleteInterview(interviewId);

        return "Interview deleted successfully";
    }
}

