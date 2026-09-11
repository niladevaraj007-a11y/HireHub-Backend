package com.Hirehub.service;

import com.Hirehub.entity.JobSeeker;
import com.Hirehub.entity.Resume;

import com.Hirehub.repository.JobSeekerRepository;
import com.Hirehub.repository.ResumeRepository;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDateTime;

import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    private final JobSeekerRepository jobSeekerRepository;

    private final String uploadDir = "uploads/resumes/";

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ResumeService(
            ResumeRepository resumeRepository,
            JobSeekerRepository jobSeekerRepository) {

        this.resumeRepository = resumeRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    // =========================================================
    // UPLOAD RESUME
    //
    // IMPORTANT:
    // Frontend sends USER ID.
    //
    // Backend finds:
    //
    // users.user_id
    //        ↓
    // job_seekers.user_id
    //        ↓
    // job_seekers.seeker_id
    //
    // Then resume is saved using seeker_id.
    // =========================================================

    public Resume uploadResume(
            Integer userId,
            MultipartFile file) throws IOException {

        // -----------------------------------------------------
        // VALIDATE USER ID
        // -----------------------------------------------------

        if (userId == null || userId <= 0) {

            throw new RuntimeException(
                    "Valid user ID is required"
            );
        }

        // -----------------------------------------------------
        // FIND JOB SEEKER USING USER ID
        // -----------------------------------------------------

        JobSeeker jobSeeker = jobSeekerRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job seeker profile not found for user ID: "
                                        + userId
                        )
                );

        // -----------------------------------------------------
        // GET REAL SEEKER ID
        // -----------------------------------------------------

        Integer seekerId = jobSeeker.getSeekerId();

        if (seekerId == null) {

            throw new RuntimeException(
                    "Job seeker ID not found"
            );
        }

        System.out.println(
                "User ID = " + userId
                        + ", Seeker ID = " + seekerId
        );

        // -----------------------------------------------------
        // VALIDATE FILE
        // -----------------------------------------------------

        if (file == null || file.isEmpty()) {

            throw new RuntimeException(
                    "Please select a resume file"
            );
        }

        // -----------------------------------------------------
        // GET ORIGINAL FILE NAME
        // -----------------------------------------------------

        String originalFileName =
                file.getOriginalFilename();

        if (originalFileName == null
                || originalFileName.trim().isEmpty()) {

            throw new RuntimeException(
                    "Resume file name is required"
            );
        }

        // -----------------------------------------------------
        // PDF VALIDATION
        // -----------------------------------------------------

        if (!originalFileName
                .toLowerCase()
                .endsWith(".pdf")) {

            throw new RuntimeException(
                    "Only PDF resume files are allowed"
            );
        }

        // -----------------------------------------------------
        // MAXIMUM 5 MB
        // -----------------------------------------------------

        if (file.getSize() > 5 * 1024 * 1024) {

            throw new RuntimeException(
                    "Resume size must be less than 5 MB"
            );
        }

        // -----------------------------------------------------
        // CLEAN FILE NAME
        // -----------------------------------------------------

        String cleanFileName =
                Paths.get(originalFileName)
                        .getFileName()
                        .toString();

        // -----------------------------------------------------
        // CREATE UPLOAD DIRECTORY
        // -----------------------------------------------------

        Path uploadPath =
                Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {

            Files.createDirectories(uploadPath);
        }

        // -----------------------------------------------------
        // GENERATE UNIQUE FILE NAME
        // -----------------------------------------------------

        String storedFileName =
                System.currentTimeMillis()
                        + "_"
                        + cleanFileName;

        Path filePath =
                uploadPath.resolve(storedFileName);

        // -----------------------------------------------------
        // SAVE PHYSICAL FILE
        // -----------------------------------------------------

        Files.copy(
                file.getInputStream(),
                filePath,
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
        );

        // -----------------------------------------------------
        // SAVE DATABASE RECORD
        // -----------------------------------------------------

        Resume resume = new Resume();

        // VERY IMPORTANT:
        // Save REAL seeker_id, NOT user_id
        resume.setSeekerId(seekerId);

        resume.setFileName(cleanFileName);

        resume.setFilePath(
                filePath.toString()
        );

        resume.setUploadedAt(
                LocalDateTime.now()
        );

        Resume savedResume =
                resumeRepository.save(resume);

        System.out.println(
                "Resume saved successfully."
        );

        System.out.println(
                "Resume ID = "
                        + savedResume.getResumeId()
        );

        System.out.println(
                "Seeker ID = "
                        + savedResume.getSeekerId()
        );

        return savedResume;
    }

    // =========================================================
    // GET ALL RESUMES
    // =========================================================

    public List<Resume> getAllResumes() {

        return resumeRepository.findAll();
    }

    // =========================================================
    // GET RESUMES BY SEEKER
    // =========================================================

    public List<Resume> getResumesBySeeker(
            Integer seekerId) {

        if (seekerId == null || seekerId <= 0) {

            throw new RuntimeException(
                    "Valid seeker ID is required"
            );
        }

        return resumeRepository
                .findBySeekerId(seekerId);
    }

    // =========================================================
    // GET RESUME BY ID
    // =========================================================

    public Resume getResumeById(
            Integer resumeId) {

        return resumeRepository
                .findById(resumeId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Resume not found"
                        )
                );
    }

    // =========================================================
    // DELETE RESUME
    // =========================================================

    public void deleteResume(
            Integer resumeId) {

        Resume resume =
                resumeRepository
                        .findById(resumeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Resume not found"
                                )
                        );

        try {

            Path filePath =
                    Paths.get(
                            resume.getFilePath()
                    );

            Files.deleteIfExists(filePath);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not delete resume file"
            );
        }

        resumeRepository.deleteById(resumeId);
    }

    // =========================================================
    // DOWNLOAD RESUME
    // =========================================================

    public byte[] downloadResume(
            Integer resumeId) {

        Resume resume =
                resumeRepository
                        .findById(resumeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Resume not found"
                                )
                        );

        try {

            Path path =
                    Paths.get(
                            resume.getFilePath()
                    );

            if (!Files.exists(path)) {

                throw new RuntimeException(
                        "Resume file not found"
                );
            }

            return Files.readAllBytes(path);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to read resume file"
            );
        }
    }
}