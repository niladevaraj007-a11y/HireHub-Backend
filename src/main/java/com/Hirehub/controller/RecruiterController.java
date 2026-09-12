package com.Hirehub.controller;

import com.Hirehub.entity.Recruiter;
import com.Hirehub.service.RecruiterService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiters")
@CrossOrigin
public class RecruiterController {

    private final RecruiterService recruiterService;

    public RecruiterController(
            RecruiterService recruiterService) {

        this.recruiterService = recruiterService;
    }

    @PostMapping
    public ResponseEntity<Recruiter> createRecruiter(
            @RequestBody Recruiter recruiter) {

        return ResponseEntity.ok(
                recruiterService.createRecruiter(recruiter)
        );
    }

    @GetMapping("/{recruiterId}")
    public ResponseEntity<Recruiter> getById(
            @PathVariable Integer recruiterId) {

        return ResponseEntity.ok(
                recruiterService.getRecruiterById(
                        recruiterId
                )
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Recruiter> getByUserId(
            @PathVariable Integer userId) {

        return ResponseEntity.ok(
                recruiterService.getRecruiterByUserId(
                        userId
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<Recruiter>>
    getAllRecruiters() {

        return ResponseEntity.ok(
                recruiterService.getAllRecruiters()
        );
    }

    @PutMapping("/{recruiterId}")
    public ResponseEntity<Recruiter> updateRecruiter(
            @PathVariable Integer recruiterId,
            @RequestBody Recruiter recruiter) {

        return ResponseEntity.ok(
                recruiterService.updateRecruiter(
                        recruiterId,
                        recruiter
                )
        );
    }

    @DeleteMapping("/{recruiterId}")
    public ResponseEntity<String> deleteRecruiter(
            @PathVariable Integer recruiterId) {

        return ResponseEntity.ok(
                recruiterService.deleteRecruiter(
                        recruiterId
                )
        );
    }
}