package com.Hirehub.service;

import com.Hirehub.entity.User;
import com.Hirehub.dto.ApplicantResponse;
import com.Hirehub.dto.ApplicationResponse;
import com.Hirehub.entity.Application;
import com.Hirehub.entity.ApplicationStatus;
import com.Hirehub.entity.Company;
import com.Hirehub.entity.Interview;
import com.Hirehub.entity.InterviewStatus;
import com.Hirehub.entity.Job;
import com.Hirehub.entity.JobSeeker;
import com.Hirehub.entity.Resume;
import com.Hirehub.repository.ApplicationRepository;
import com.Hirehub.repository.CompanyRepository;
import com.Hirehub.repository.InterviewRepository;
import com.Hirehub.repository.JobRepository;
import com.Hirehub.repository.JobSeekerRepository;
import com.Hirehub.repository.ResumeRepository;
import com.Hirehub.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ResumeRepository resumeRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final InterviewRepository interviewRepository;
    private final NotificationService notificationService;
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ApplicationService(

            ApplicationRepository applicationRepository,

            ResumeRepository resumeRepository,

            JobSeekerRepository jobSeekerRepository,

            InterviewRepository interviewRepository,

            NotificationService notificationService,

            JobRepository jobRepository,

            CompanyRepository companyRepository,

            UserRepository userRepository) {

        this.applicationRepository = applicationRepository;
        this.resumeRepository = resumeRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.interviewRepository = interviewRepository;
        this.notificationService = notificationService;
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }
    // =========================================================
    // GET ALL APPLICATIONS
    // =========================================================

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // =========================================================
    // GET APPLICATION BY ID
    // =========================================================

    public Application getApplicationById(Integer applicationId) {

        validateId(applicationId, "application");

        return applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));
    }

    // =========================================================
    // APPLY FOR A JOB
    // =========================================================

    public Application applyForJob(
            Integer jobId,
            Integer seekerId,
            Integer resumeId) {

        // -----------------------------------------------------
        // VALIDATE IDS
        // -----------------------------------------------------

        validateId(jobId, "job");
        validateId(seekerId, "seeker");
        validateId(resumeId, "resume");

        // -----------------------------------------------------
        // CHECK JOB
        // -----------------------------------------------------

        jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        // -----------------------------------------------------
        // CHECK DUPLICATE APPLICATION
        // -----------------------------------------------------

        if (applicationRepository.existsByJobIdAndSeekerId(
                jobId,
                seekerId)) {

            throw new RuntimeException(
                    "You have already applied for this job");
        }

        // -----------------------------------------------------
        // CHECK RESUME
        // -----------------------------------------------------

        Resume resume = resumeRepository
                .findById(resumeId)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found"));

        // -----------------------------------------------------
        // CHECK RESUME OWNER
        // -----------------------------------------------------

        if (resume.getSeekerId() == null
                || !resume.getSeekerId().equals(seekerId)) {

            throw new RuntimeException(
                    "This resume does not belong to the seeker");
        }

        // -----------------------------------------------------
        // CREATE APPLICATION
        // -----------------------------------------------------

        Application application = new Application();

        application.setJobId(jobId);
        application.setSeekerId(seekerId);
        application.setResumeId(resumeId);
        application.setAppliedAt(LocalDateTime.now());

        // -----------------------------------------------------
        // DEFAULT STATUS
        // -----------------------------------------------------

        application.setStatus(ApplicationStatus.APPLIED);

        return applicationRepository.save(application);
    }

    // =========================================================
    // GET APPLICATIONS BY SEEKER
    // =========================================================

    public List<ApplicationResponse> getApplicationsBySeeker(
            Integer seekerId) {

        validateId(seekerId, "seeker");

        List<Application> applications =
                applicationRepository.findBySeekerId(seekerId);

        return applications.stream()
                .map(application -> {

                    Job job = jobRepository
                            .findById(application.getJobId())
                            .orElse(null);

                    String jobTitle = "Job Application";
                    String location = "Location not specified";
                    String companyName = "Company";

                    // -------------------------------------------------
                    // JOB DETAILS
                    // -------------------------------------------------

                    if (job != null) {

                        if (job.getTitle() != null
                                && !job.getTitle().trim().isEmpty()) {

                            jobTitle = job.getTitle();
                        }

                        if (job.getLocation() != null
                                && !job.getLocation().trim().isEmpty()) {

                            location = job.getLocation();
                        }

                        // -------------------------------------------------
                        // COMPANY DETAILS
                        // -------------------------------------------------

                        if (job.getCompanyId() != null) {

                            Company company =
                                    companyRepository
                                            .findById(job.getCompanyId())
                                            .orElse(null);

                            if (company != null
                                    && company.getCompanyName() != null
                                    && !company.getCompanyName()
                                            .trim()
                                            .isEmpty()) {

                                companyName =
                                        company.getCompanyName();
                            }
                        }
                    }

                    // -------------------------------------------------
                    // APPLICATION STATUS
                    // -------------------------------------------------

                    String applicationStatus =
                            application.getStatus() != null
                                    ? application.getStatus().name()
                                    : ApplicationStatus.APPLIED.name();

                    // -------------------------------------------------
                    // APPLICATION RESPONSE
                    // -------------------------------------------------

                    return new ApplicationResponse(
                            application.getApplicationId(),
                            application.getJobId(),
                            application.getSeekerId(),
                            application.getResumeId(),
                            jobTitle,
                            companyName,
                            location,
                            applicationStatus,
                            application.getAppliedAt()
                    );
                })
                .toList();
    }

    // =========================================================
    // GET APPLICATIONS BY JOB
    // =========================================================

    public List<Application> getApplicationsByJob(
            Integer jobId) {

        validateId(jobId, "job");

        return applicationRepository.findByJobId(jobId);
    }
 // =========================================================
 // GET FULL APPLICANT DETAILS BY JOB
 // GET /api/applications/job/{jobId}
 // =========================================================

 public List<ApplicantResponse> getApplicantsByJob(Integer jobId) {

     validateId(jobId, "job");

     List<Application> applications =
             applicationRepository.findByJobId(jobId);

     return applications.stream()
             .map(application ->
                     buildApplicantResponse(application))
             .toList();
 }

    // =========================================================
    // UPDATE APPLICATION STATUS
    // =========================================================

    public Application updateApplicationStatus(
            Integer applicationId,
            String status) {

        // -----------------------------------------------------
        // FIND APPLICATION
        // -----------------------------------------------------

        Application application =
                applicationRepository
                        .findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"));

        // -----------------------------------------------------
        // VALIDATE STATUS
        // -----------------------------------------------------

        if (status == null || status.trim().isEmpty()) {

            throw new RuntimeException(
                    "Application status is required");
        }

        ApplicationStatus newStatus;

        try {

            newStatus = ApplicationStatus.valueOf(
                    status.trim().toUpperCase());

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Invalid application status: "
                            + status
                            + ". Allowed values: "
                            + "APPLIED, SHORTLISTED, SELECTED, REJECTED");
        }

        // -----------------------------------------------------
        // OLD STATUS
        // -----------------------------------------------------

        ApplicationStatus oldStatus =
                application.getStatus();

        if (oldStatus == null) {
            oldStatus = ApplicationStatus.APPLIED;
        }

        // -----------------------------------------------------
        // SAME STATUS
        // -----------------------------------------------------

        if (oldStatus == newStatus) {
            return application;
        }

        // -----------------------------------------------------
        // VALIDATE STATUS TRANSITION
        // -----------------------------------------------------

        validateStatusTransition(
                application,
                oldStatus,
                newStatus);

        // -----------------------------------------------------
        // UPDATE STATUS
        // -----------------------------------------------------

        application.setStatus(newStatus);

        Application savedApplication =
                applicationRepository.save(application);

        // -----------------------------------------------------
        // FIND JOB SEEKER
        // -----------------------------------------------------

        JobSeeker jobSeeker =
                jobSeekerRepository
                        .findById(application.getSeekerId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job seeker not found"));

        Integer userId = jobSeeker.getUserId();

        // -----------------------------------------------------
        // SEND NOTIFICATION
        // -----------------------------------------------------

        if (userId != null) {

            sendStatusNotification(
                    userId,
                    newStatus);
        }

        return savedApplication;
    }

    // =========================================================
    // VALIDATE STATUS TRANSITION
    // =========================================================

    private void validateStatusTransition(
            Application application,
            ApplicationStatus oldStatus,
            ApplicationStatus newStatus) {

        // =====================================================
        // APPLIED
        // =====================================================

        if (oldStatus == ApplicationStatus.APPLIED) {

            if (newStatus == ApplicationStatus.SHORTLISTED
                    || newStatus == ApplicationStatus.REJECTED) {

                return;
            }

            throw new RuntimeException(
                    "Invalid status transition: "
                            + oldStatus
                            + " -> "
                            + newStatus
                            + ". APPLIED can only change to "
                            + "SHORTLISTED or REJECTED.");
        }

        // =====================================================
        // SHORTLISTED
        // =====================================================

        if (oldStatus == ApplicationStatus.SHORTLISTED) {

            // -------------------------------------------------
            // SHORTLISTED -> REJECTED
            // -------------------------------------------------

            if (newStatus == ApplicationStatus.REJECTED) {
                return;
            }

            // -------------------------------------------------
            // SHORTLISTED -> SELECTED
            // -------------------------------------------------

            if (newStatus == ApplicationStatus.SELECTED) {

                validateCompletedInterview(application);

                return;
            }

            throw new RuntimeException(
                    "Invalid status transition: "
                            + oldStatus
                            + " -> "
                            + newStatus
                            + ". SHORTLISTED can only change to "
                            + "REJECTED or SELECTED after a "
                            + "completed interview.");
        }

        // =====================================================
        // SELECTED
        // =====================================================

        if (oldStatus == ApplicationStatus.SELECTED) {

            throw new RuntimeException(
                    "Invalid status transition: "
                            + oldStatus
                            + " -> "
                            + newStatus
                            + ". SELECTED is a final status.");
        }

        // =====================================================
        // REJECTED
        // =====================================================

        if (oldStatus == ApplicationStatus.REJECTED) {

            throw new RuntimeException(
                    "Invalid status transition: "
                            + oldStatus
                            + " -> "
                            + newStatus
                            + ". REJECTED is a final status.");
        }

        throw new RuntimeException(
                "Invalid current application status: "
                        + oldStatus);
    }

    // =========================================================
    // VALIDATE COMPLETED INTERVIEW
    // =========================================================

    private void validateCompletedInterview(
            Application application) {

        List<Interview> interviews =
                interviewRepository.findByApplicationId(
                        application.getApplicationId());

        // -----------------------------------------------------
        // NO INTERVIEW
        // -----------------------------------------------------

        if (interviews == null || interviews.isEmpty()) {

            throw new RuntimeException(
                    "Candidate cannot be selected because "
                            + "no interview has been scheduled "
                            + "for this application.");
        }

        // -----------------------------------------------------
        // CHECK COMPLETED INTERVIEW
        // -----------------------------------------------------

        boolean completedInterview =
                interviews.stream()
                        .anyMatch(interview ->
                                interview.getStatus()
                                        == InterviewStatus.COMPLETED);

        if (!completedInterview) {

            throw new RuntimeException(
                    "Candidate cannot be selected because "
                            + "the interview has not been completed.");
        }
    }

    // =========================================================
    // SEND STATUS NOTIFICATION
    // =========================================================

    private void sendStatusNotification(
            Integer userId,
            ApplicationStatus status) {

        if (status == ApplicationStatus.SHORTLISTED) {

            notificationService.createNotification(
                    userId,
                    "Your application has been shortlisted");

            return;
        }

        if (status == ApplicationStatus.SELECTED) {

            notificationService.createNotification(
                    userId,
                    "Congratulations! You have been selected for the job");

            return;
        }

        if (status == ApplicationStatus.REJECTED) {

            notificationService.createNotification(
                    userId,
                    "Your application has been rejected");

            return;
        }

        if (status == ApplicationStatus.APPLIED) {

            notificationService.createNotification(
                    userId,
                    "Your application status has been changed to applied");
        }
    }

    // =========================================================
    // VALIDATE ID
    // =========================================================

    private void validateId(
            Integer id,
            String entityName) {

        if (id == null || id <= 0) {

            throw new RuntimeException(
                    "Valid " + entityName + " ID is required");
        }
    }
 // =========================================================
 // BUILD APPLICANT RESPONSE
 // =========================================================

 private ApplicantResponse buildApplicantResponse(
         Application application) {

     JobSeeker jobSeeker =
             jobSeekerRepository.findById(
                     application.getSeekerId()
             ).orElse(null);

     if (jobSeeker == null) {
         throw new RuntimeException(
                 "Job seeker not found for application: "
                         + application.getApplicationId());
     }

     User user =
             userRepository.findById(
                     jobSeeker.getUserId()
             ).orElse(null);

     if (user == null) {
         throw new RuntimeException(
                 "User not found for job seeker: "
                         + jobSeeker.getSeekerId());
     }

     Resume resume = null;

     if (application.getResumeId() != null) {
         resume = resumeRepository.findById(
                 application.getResumeId()
         ).orElse(null);
     }

     ApplicantResponse response =
             new ApplicantResponse();

     // Application
     response.setApplicationId(
             application.getApplicationId());

     response.setSeekerId(
             application.getSeekerId());

     response.setResumeId(
             application.getResumeId());

     response.setStatus(
             application.getStatus() != null
                     ? application.getStatus().name()
                     : "APPLIED");

     response.setAppliedAt(
             application.getAppliedAt() != null
                     ? application.getAppliedAt().toString()
                     : null);

     // Job seeker
     response.setHeadline(
             jobSeeker.getHeadline());

     response.setAbout(
             jobSeeker.getAbout());

     response.setEducation(
             jobSeeker.getEducation());

     response.setExperienceYears(
             jobSeeker.getExperienceYears());

     response.setLocation(
             jobSeeker.getLocation());

     // User
     response.setUserId(
             user.getUserId());

     response.setFullName(
             user.getFullName());

     response.setEmail(
             user.getEmail());

     response.setPhone(
             user.getPhone());

     // Resume
     if (resume != null) {
         response.setResumeFileName(
                 resume.getFileName());
     }

     return response;
 }
}