package com.Hirehub.controller;

import com.Hirehub.entity.Resume;
import com.Hirehub.service.ResumeService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@CrossOrigin(origins = "*")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    // =========================================================
    // UPLOAD RESUME
    //
    // POST /api/resumes/upload?userId=22
    //
    // Frontend sends USER ID.
    // ResumeService finds the corresponding seekerId.
    // =========================================================

    @PostMapping("/upload")
    public ResponseEntity<Resume> uploadResume(
            @RequestParam("userId") Integer userId,
            @RequestParam("file") MultipartFile file)
            throws IOException {

        Resume resume = resumeService.uploadResume(
                userId,
                file
        );

        return ResponseEntity.ok(resume);
    }

    // =========================================================
    // GET ALL RESUMES
    //
    // GET /api/resumes
    // =========================================================

    @GetMapping
    public ResponseEntity<List<Resume>> getAllResumes() {

        return ResponseEntity.ok(
                resumeService.getAllResumes()
        );
    }

    // =========================================================
    // GET RESUMES BY SEEKER
    //
    // GET /api/resumes/seeker/{seekerId}
    // =========================================================

    @GetMapping("/seeker/{seekerId}")
    public ResponseEntity<List<Resume>> getResumesBySeeker(
            @PathVariable("seekerId") Integer seekerId) {

        return ResponseEntity.ok(
                resumeService.getResumesBySeeker(seekerId)
        );
    }

    // =========================================================
    // GET RESUME BY ID
    //
    // GET /api/resumes/{resumeId}
    // =========================================================

    @GetMapping("/{resumeId}")
    public ResponseEntity<Resume> getResume(
            @PathVariable("resumeId") Integer resumeId) {

        return ResponseEntity.ok(
                resumeService.getResumeById(resumeId)
        );
    }

    // =========================================================
    // VIEW / DOWNLOAD RESUME
    //
    // GET /api/resumes/{resumeId}/download
    // =========================================================

    @GetMapping("/{resumeId}/download")
    public ResponseEntity<byte[]> downloadResume(
            @PathVariable("resumeId") Integer resumeId) {

        Resume resume = resumeService.getResumeById(resumeId);

        byte[] file = resumeService.downloadResume(resumeId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resume.getFileName() + "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }

    // =========================================================
    // DELETE RESUME
    //
    // DELETE /api/resumes/{resumeId}
    // =========================================================

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<String> deleteResume(
            @PathVariable("resumeId") Integer resumeId) {

        resumeService.deleteResume(resumeId);

        return ResponseEntity.ok(
                "Resume deleted successfully"
        );
    }
}