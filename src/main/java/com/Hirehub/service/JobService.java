
package com.Hirehub.service;

import com.Hirehub.entity.Company;
import com.Hirehub.entity.Job;
import com.Hirehub.entity.Recruiter;

import com.Hirehub.repository.CompanyRepository;
import com.Hirehub.repository.JobRepository;
import com.Hirehub.repository.RecruiterRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final RecruiterRepository recruiterRepository;

    public JobService(
            JobRepository jobRepository,
            CompanyRepository companyRepository,
            RecruiterRepository recruiterRepository) {

        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.recruiterRepository = recruiterRepository;
    }

    // =========================================================
    // GET ALL JOBS
    // =========================================================

    public List<Job> getAllJobs() {

        return jobRepository.findAll();
    }

    // =========================================================
    // GET JOB BY ID
    // =========================================================

    public Job getJobById(Integer id) {

        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));
    }

    // =========================================================
    // GET JOBS BY COMPANY
    // =========================================================

    public List<Job> getJobsByCompany(Integer companyId) {

        return jobRepository.findByCompanyId(companyId);
    }

    // =========================================================
    // CREATE JOB
    // =========================================================

    public Job createJob(
            Integer recruiterUserId,
            Job job) {

        // -----------------------------------------------------
        // Find recruiter
        // -----------------------------------------------------

        Recruiter recruiter =
                recruiterRepository
                        .findByUserId(recruiterUserId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter profile not found"));

        // -----------------------------------------------------
        // Get recruiter's company
        // -----------------------------------------------------

        Integer recruiterCompanyId =
                recruiter.getCompanyId();

        if (recruiterCompanyId == null) {

            throw new RuntimeException(
                    "Recruiter is not associated with a company");
        }

        // -----------------------------------------------------
        // Check company exists
        // -----------------------------------------------------

        Company company =
                companyRepository
                        .findById(recruiterCompanyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found"));

        // -----------------------------------------------------
        // Validate job
        // -----------------------------------------------------

        validateJob(job);

        // -----------------------------------------------------
        // IMPORTANT:
        // Never trust companyId from frontend
        // -----------------------------------------------------

        job.setCompanyId(recruiterCompanyId);

        // -----------------------------------------------------
        // Default status
        // -----------------------------------------------------

        if (job.getStatus() == null) {

            job.setStatus(
                    Job.JobStatus.OPEN);
        }

        // -----------------------------------------------------
        // Default job type
        // -----------------------------------------------------

        if (job.getJobType() == null) {

            job.setJobType(
                    Job.JobType.FULL_TIME);
        }

        // -----------------------------------------------------
        // Set creation time
        // -----------------------------------------------------

        if (job.getCreatedAt() == null) {

            job.setCreatedAt(
                    LocalDateTime.now());
        }

        return jobRepository.save(job);
    }

    // =========================================================
    // UPDATE JOB
    // =========================================================

    public Job updateJob(
            Integer jobId,
            Integer recruiterUserId,
            Job updatedJob) {

        // -----------------------------------------------------
        // Find existing job
        // -----------------------------------------------------

        Job existingJob =
                jobRepository.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found"));

        // -----------------------------------------------------
        // Find recruiter
        // -----------------------------------------------------

        Recruiter recruiter =
                recruiterRepository
                        .findByUserId(recruiterUserId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter profile not found"));

        Integer recruiterCompanyId =
                recruiter.getCompanyId();

        if (recruiterCompanyId == null) {

            throw new RuntimeException(
                    "Recruiter is not associated with a company");
        }

        // -----------------------------------------------------
        // Authorization
        // -----------------------------------------------------

        if (existingJob.getCompanyId() == null ||
                !existingJob.getCompanyId()
                        .equals(recruiterCompanyId)) {

            throw new RuntimeException(
                    "You are not authorized to edit this job");
        }

        // -----------------------------------------------------
        // Validate updated values
        // -----------------------------------------------------

        validateUpdatedJob(updatedJob);

        // -----------------------------------------------------
        // Update title
        // -----------------------------------------------------

        if (updatedJob.getTitle() != null) {

            existingJob.setTitle(
                    updatedJob.getTitle());
        }

        // -----------------------------------------------------
        // Update description
        // -----------------------------------------------------

        if (updatedJob.getDescription() != null) {

            existingJob.setDescription(
                    updatedJob.getDescription());
        }

        // -----------------------------------------------------
        // Update required skills
        // -----------------------------------------------------

        if (updatedJob.getRequiredSkills() != null) {

            existingJob.setRequiredSkills(
                    updatedJob.getRequiredSkills());
        }

        // -----------------------------------------------------
        // Update location
        // -----------------------------------------------------

        if (updatedJob.getLocation() != null) {

            existingJob.setLocation(
                    updatedJob.getLocation());
        }

        // -----------------------------------------------------
        // Update experience
        // -----------------------------------------------------

        if (updatedJob.getExperienceRequired() != null) {

            existingJob.setExperienceRequired(
                    updatedJob.getExperienceRequired());
        }

        // -----------------------------------------------------
        // Update minimum salary
        // -----------------------------------------------------

        if (updatedJob.getMinSalary() != null) {

            existingJob.setMinSalary(
                    updatedJob.getMinSalary());
        }

        // -----------------------------------------------------
        // Update maximum salary
        // -----------------------------------------------------

        if (updatedJob.getMaxSalary() != null) {

            existingJob.setMaxSalary(
                    updatedJob.getMaxSalary());
        }

        // -----------------------------------------------------
        // Update job type
        // -----------------------------------------------------

        if (updatedJob.getJobType() != null) {

            existingJob.setJobType(
                    updatedJob.getJobType());
        }

        // -----------------------------------------------------
        // Update status
        // -----------------------------------------------------

        if (updatedJob.getStatus() != null) {

            existingJob.setStatus(
                    updatedJob.getStatus());
        }

        // -----------------------------------------------------
        // Update vacancies
        // -----------------------------------------------------

        if (updatedJob.getVacancies() != null) {

            existingJob.setVacancies(
                    updatedJob.getVacancies());
        }

        // -----------------------------------------------------
        // Company cannot be changed
        // -----------------------------------------------------

        existingJob.setCompanyId(
                recruiterCompanyId);

        return jobRepository.save(existingJob);
    }

    // =========================================================
    // DELETE JOB
    // =========================================================

    public void deleteJob(
            Integer jobId,
            Integer recruiterUserId) {

        // -----------------------------------------------------
        // Find job
        // -----------------------------------------------------

        Job existingJob =
                jobRepository.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found"));

        // -----------------------------------------------------
        // Find recruiter
        // -----------------------------------------------------

        Recruiter recruiter =
                recruiterRepository
                        .findByUserId(recruiterUserId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter profile not found"));

        Integer recruiterCompanyId =
                recruiter.getCompanyId();

        if (recruiterCompanyId == null) {

            throw new RuntimeException(
                    "Recruiter is not associated with a company");
        }

        // -----------------------------------------------------
        // Authorization
        // -----------------------------------------------------

        if (existingJob.getCompanyId() == null ||
                !existingJob.getCompanyId()
                        .equals(recruiterCompanyId)) {

            throw new RuntimeException(
                    "You are not authorized to delete this job");
        }

        // -----------------------------------------------------
        // Delete
        // -----------------------------------------------------

        jobRepository.deleteById(jobId);
    }

    // =========================================================
    // VALIDATE NEW JOB
    // =========================================================

    private void validateJob(Job job) {

        if (job == null) {

            throw new RuntimeException(
                    "Job data is required");
        }

        if (job.getTitle() == null ||
                job.getTitle().trim().isEmpty()) {

            throw new RuntimeException(
                    "Job title is required");
        }

        if (job.getDescription() == null ||
                job.getDescription().trim().isEmpty()) {

            throw new RuntimeException(
                    "Job description is required");
        }

        if (job.getLocation() == null ||
                job.getLocation().trim().isEmpty()) {

            throw new RuntimeException(
                    "Job location is required");
        }

        // -----------------------------------------------------
        // Experience validation
        // -----------------------------------------------------

        if (job.getExperienceRequired() != null &&
                job.getExperienceRequired()
                        .compareTo(BigDecimal.ZERO) < 0) {

            throw new RuntimeException(
                    "Experience cannot be negative");
        }

        // -----------------------------------------------------
        // Minimum salary validation
        // -----------------------------------------------------

        if (job.getMinSalary() != null &&
                job.getMinSalary()
                        .compareTo(BigDecimal.ZERO) < 0) {

            throw new RuntimeException(
                    "Minimum salary cannot be negative");
        }

        // -----------------------------------------------------
        // Maximum salary validation
        // -----------------------------------------------------

        if (job.getMaxSalary() != null &&
                job.getMaxSalary()
                        .compareTo(BigDecimal.ZERO) < 0) {

            throw new RuntimeException(
                    "Maximum salary cannot be negative");
        }

        // -----------------------------------------------------
        // Salary range validation
        // -----------------------------------------------------

        if (job.getMinSalary() != null &&
                job.getMaxSalary() != null &&
                job.getMinSalary()
                        .compareTo(job.getMaxSalary()) > 0) {

            throw new RuntimeException(
                    "Minimum salary cannot be greater than maximum salary");
        }

        // -----------------------------------------------------
        // Vacancies validation
        // -----------------------------------------------------

        if (job.getVacancies() != null &&
                job.getVacancies() <= 0) {

            throw new RuntimeException(
                    "Vacancies must be greater than zero");
        }
    }

    // =========================================================
    // VALIDATE UPDATED JOB
    // =========================================================

    private void validateUpdatedJob(Job job) {

        if (job == null) {

            throw new RuntimeException(
                    "Job data is required");
        }

        // -----------------------------------------------------
        // Title
        // -----------------------------------------------------

        if (job.getTitle() != null &&
                job.getTitle().trim().isEmpty()) {

            throw new RuntimeException(
                    "Job title cannot be empty");
        }

        // -----------------------------------------------------
        // Description
        // -----------------------------------------------------

        if (job.getDescription() != null &&
                job.getDescription().trim().isEmpty()) {

            throw new RuntimeException(
                    "Job description cannot be empty");
        }

        // -----------------------------------------------------
        // Location
        // -----------------------------------------------------

        if (job.getLocation() != null &&
                job.getLocation().trim().isEmpty()) {

            throw new RuntimeException(
                    "Job location cannot be empty");
        }

        // -----------------------------------------------------
        // Experience
        // -----------------------------------------------------

        if (job.getExperienceRequired() != null &&
                job.getExperienceRequired()
                        .compareTo(BigDecimal.ZERO) < 0) {

            throw new RuntimeException(
                    "Experience cannot be negative");
        }

        // -----------------------------------------------------
        // Minimum salary
        // -----------------------------------------------------

        if (job.getMinSalary() != null &&
                job.getMinSalary()
                        .compareTo(BigDecimal.ZERO) < 0) {

            throw new RuntimeException(
                    "Minimum salary cannot be negative");
        }

        // -----------------------------------------------------
        // Maximum salary
        // -----------------------------------------------------

        if (job.getMaxSalary() != null &&
                job.getMaxSalary()
                        .compareTo(BigDecimal.ZERO) < 0) {

            throw new RuntimeException(
                    "Maximum salary cannot be negative");
        }

        // -----------------------------------------------------
        // Salary range
        // -----------------------------------------------------

        if (job.getMinSalary() != null &&
                job.getMaxSalary() != null &&
                job.getMinSalary()
                        .compareTo(job.getMaxSalary()) > 0) {

            throw new RuntimeException(
                    "Minimum salary cannot be greater than maximum salary");
        }

        // -----------------------------------------------------
        // Vacancies
        // -----------------------------------------------------

        if (job.getVacancies() != null &&
                job.getVacancies() <= 0) {

            throw new RuntimeException(
                    "Vacancies must be greater than zero");
        }
    }
}

