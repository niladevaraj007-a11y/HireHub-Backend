package com.Hirehub.service;

import com.Hirehub.entity.Application;
import com.Hirehub.entity.Interview;
import com.Hirehub.entity.InterviewStatus;
import com.Hirehub.entity.InterviewType;
import com.Hirehub.entity.JobSeeker;
import com.Hirehub.repository.ApplicationRepository;
import com.Hirehub.repository.InterviewRepository;
import com.Hirehub.repository.JobSeekerRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final NotificationService notificationService;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public InterviewService(
            InterviewRepository interviewRepository,
            ApplicationRepository applicationRepository,
            JobSeekerRepository jobSeekerRepository,
            NotificationService notificationService) {

        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.notificationService = notificationService;
    }

    // =========================================================
    // CREATE INTERVIEW
    // =========================================================

    public Interview createInterview(Interview interview) {

        if (interview == null) {
            throw new RuntimeException(
                    "Interview details are required");
        }

        // -----------------------------------------------------
        // VALIDATE APPLICATION ID
        // -----------------------------------------------------

        if (interview.getApplicationId() == null
                || interview.getApplicationId() <= 0) {

            throw new RuntimeException(
                    "Valid application ID is required");
        }

        // -----------------------------------------------------
        // CHECK APPLICATION EXISTS
        // -----------------------------------------------------

        Application application =
                applicationRepository
                        .findById(interview.getApplicationId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"));

        // -----------------------------------------------------
        // CHECK INTERVIEW DATE
        // -----------------------------------------------------

        if (interview.getInterviewDate() == null) {

            throw new RuntimeException(
                    "Interview date is required");
        }

        if (interview.getInterviewDate()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Interview date cannot be in the past");
        }

        // -----------------------------------------------------
        // CHECK INTERVIEW TYPE
        // -----------------------------------------------------

        if (interview.getInterviewType() == null) {

            throw new RuntimeException(
                    "Interview type is required");
        }

        // -----------------------------------------------------
        // CHECK APPLICATION STATUS
        // -----------------------------------------------------
        // Interview should normally be scheduled only after
        // the candidate has been shortlisted.
        // -----------------------------------------------------

        if (application.getStatus() == null) {

            throw new RuntimeException(
                    "Application status is not available");
        }

        if (!application.getStatus().name()
                .equals("SHORTLISTED")) {

            throw new RuntimeException(
                    "Interview can only be scheduled "
                            + "for a shortlisted candidate");
        }

        // -----------------------------------------------------
        // ONLINE INTERVIEW
        // -----------------------------------------------------

        if (interview.getInterviewType()
                == InterviewType.ONLINE) {

            if (interview.getMeetingLink() == null
                    || interview.getMeetingLink()
                    .trim()
                    .isEmpty()) {

                throw new RuntimeException(
                        "Meeting link is required for online interview");
            }

            // Online interview does not need location
            interview.setLocation(null);
        }

        // -----------------------------------------------------
        // OFFLINE INTERVIEW
        // -----------------------------------------------------

        if (interview.getInterviewType()
                == InterviewType.OFFLINE) {

            if (interview.getLocation() == null
                    || interview.getLocation()
                    .trim()
                    .isEmpty()) {

                throw new RuntimeException(
                        "Location is required for offline interview");
            }

            // Offline interview does not need meeting link
            interview.setMeetingLink(null);
        }

        // -----------------------------------------------------
        // PHONE INTERVIEW
        // -----------------------------------------------------

        if (interview.getInterviewType()
                == InterviewType.PHONE) {

            // Phone interview does not require
            // meeting link or location.

            interview.setMeetingLink(null);
            interview.setLocation(null);
        }

        // -----------------------------------------------------
        // DEFAULT STATUS
        // -----------------------------------------------------

        interview.setStatus(
                InterviewStatus.SCHEDULED);

        // -----------------------------------------------------
        // SAVE INTERVIEW FIRST
        // -----------------------------------------------------

        Interview savedInterview =
                interviewRepository.save(interview);

        // -----------------------------------------------------
        // SEND NOTIFICATION AFTER SUCCESSFUL SAVE
        // -----------------------------------------------------

        sendInterviewNotification(
                application,
                buildScheduledMessage(savedInterview)
        );

        return savedInterview;
    }

    // =========================================================
    // GET INTERVIEWS BY APPLICATION
    // =========================================================

    public List<Interview> getInterviewsByApplication(
            Integer applicationId) {

        if (applicationId == null
                || applicationId <= 0) {

            throw new RuntimeException(
                    "Valid application ID is required");
        }

        // -----------------------------------------------------
        // CHECK APPLICATION EXISTS
        // -----------------------------------------------------

        applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found"));

        return interviewRepository
                .findByApplicationId(applicationId);
    }

    // =========================================================
    // GET INTERVIEWS BY SEEKER
    // =========================================================

    public List<Interview> getInterviewsBySeeker(
            Integer seekerId) {

        if (seekerId == null
                || seekerId <= 0) {

            throw new RuntimeException(
                    "Valid seeker ID is required");
        }

        // -----------------------------------------------------
        // FIND APPLICATIONS OF SEEKER
        // -----------------------------------------------------

        List<Application> applications =
                applicationRepository
                        .findBySeekerId(seekerId);

        // -----------------------------------------------------
        // NO APPLICATIONS
        // -----------------------------------------------------

        if (applications.isEmpty()) {
            return new ArrayList<>();
        }

        // -----------------------------------------------------
        // FIND INTERVIEWS FOR EACH APPLICATION
        // -----------------------------------------------------

        List<Interview> interviews =
                new ArrayList<>();

        for (Application application : applications) {

            List<Interview> applicationInterviews =
                    interviewRepository
                            .findByApplicationId(
                                    application.getApplicationId());

            interviews.addAll(applicationInterviews);
        }

        return interviews;
    }

    // =========================================================
    // GET INTERVIEW BY ID
    // =========================================================

    public Interview getInterviewById(
            Integer interviewId) {

        if (interviewId == null
                || interviewId <= 0) {

            throw new RuntimeException(
                    "Valid interview ID is required");
        }

        return interviewRepository
                .findById(interviewId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Interview not found"));
    }

    // =========================================================
    // UPDATE COMPLETE INTERVIEW
    // =========================================================

    public Interview updateInterview(
            Integer interviewId,
            Interview updatedInterview) {

        Interview existingInterview =
                getInterviewById(interviewId);

        if (updatedInterview == null) {

            throw new RuntimeException(
                    "Interview details are required");
        }

        // -----------------------------------------------------
        // DATE
        // -----------------------------------------------------

        if (updatedInterview.getInterviewDate() != null) {

            if (updatedInterview.getInterviewDate()
                    .isBefore(LocalDateTime.now())) {

                throw new RuntimeException(
                        "Interview date cannot be in the past");
            }

            existingInterview.setInterviewDate(
                    updatedInterview.getInterviewDate());
        }

        // -----------------------------------------------------
        // TYPE
        // -----------------------------------------------------

        if (updatedInterview.getInterviewType() != null) {

            existingInterview.setInterviewType(
                    updatedInterview.getInterviewType());
        }

        // -----------------------------------------------------
        // ONLINE
        // -----------------------------------------------------

        if (existingInterview.getInterviewType()
                == InterviewType.ONLINE) {

            if (updatedInterview.getMeetingLink() == null
                    || updatedInterview.getMeetingLink()
                    .trim()
                    .isEmpty()) {

                throw new RuntimeException(
                        "Meeting link is required for online interview");
            }

            existingInterview.setMeetingLink(
                    updatedInterview.getMeetingLink());

            existingInterview.setLocation(null);
        }

        // -----------------------------------------------------
        // OFFLINE
        // -----------------------------------------------------

        if (existingInterview.getInterviewType()
                == InterviewType.OFFLINE) {

            if (updatedInterview.getLocation() == null
                    || updatedInterview.getLocation()
                    .trim()
                    .isEmpty()) {

                throw new RuntimeException(
                        "Location is required for offline interview");
            }

            existingInterview.setLocation(
                    updatedInterview.getLocation());

            existingInterview.setMeetingLink(null);
        }

        // -----------------------------------------------------
        // PHONE
        // -----------------------------------------------------

        if (existingInterview.getInterviewType()
                == InterviewType.PHONE) {

            existingInterview.setMeetingLink(null);
            existingInterview.setLocation(null);
        }

        // -----------------------------------------------------
        // NOTES
        // -----------------------------------------------------

        existingInterview.setNotes(
                updatedInterview.getNotes());

        // -----------------------------------------------------
        // SAVE
        // -----------------------------------------------------

        return interviewRepository.save(
                existingInterview);
    }

    // =========================================================
    // UPDATE INTERVIEW STATUS
    // =========================================================

    public Interview updateInterviewStatus(
            Integer interviewId,
            String status) {

        Interview interview =
                getInterviewById(interviewId);

        if (status == null
                || status.trim().isEmpty()) {

            throw new RuntimeException(
                    "Interview status is required");
        }

        // -----------------------------------------------------
        // CONVERT STRING TO ENUM
        // -----------------------------------------------------

        InterviewStatus newStatus;

        try {

            newStatus =
                    InterviewStatus.valueOf(
                            status.trim().toUpperCase());

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Invalid interview status: "
                            + status
                            + ". Allowed values: "
                            + "SCHEDULED, COMPLETED, CANCELLED");
        }

        InterviewStatus oldStatus =
                interview.getStatus();

        // -----------------------------------------------------
        // SAME STATUS
        // -----------------------------------------------------

        if (oldStatus == newStatus) {
            return interview;
        }

        // -----------------------------------------------------
        // SCHEDULED → COMPLETED / CANCELLED
        // -----------------------------------------------------

        if (oldStatus == InterviewStatus.SCHEDULED) {

            if (newStatus == InterviewStatus.COMPLETED
                    || newStatus == InterviewStatus.CANCELLED) {

                interview.setStatus(newStatus);

                // -------------------------------------------------
                // SAVE FIRST
                // -------------------------------------------------

                Interview savedInterview =
                        interviewRepository.save(interview);

                // -------------------------------------------------
                // SEND NOTIFICATION
                // -------------------------------------------------

                if (newStatus
                        == InterviewStatus.COMPLETED) {

                    sendInterviewNotification(
                            interview.getApplicationId(),
                            "Your interview has been completed."
                    );
                }

                if (newStatus
                        == InterviewStatus.CANCELLED) {

                    sendInterviewNotification(
                            interview.getApplicationId(),
                            "Your interview has been cancelled."
                    );
                }

                return savedInterview;
            }

            throw new RuntimeException(
                    "Scheduled interview can only be "
                            + "COMPLETED or CANCELLED");
        }

        // -----------------------------------------------------
        // COMPLETED IS FINAL
        // -----------------------------------------------------

        if (oldStatus == InterviewStatus.COMPLETED) {

            throw new RuntimeException(
                    "Completed interview is a final status");
        }

        // -----------------------------------------------------
        // CANCELLED IS FINAL
        // -----------------------------------------------------

        if (oldStatus == InterviewStatus.CANCELLED) {

            throw new RuntimeException(
                    "Cancelled interview is a final status");
        }

        throw new RuntimeException(
                "Invalid interview status transition");
    }

    // =========================================================
    // DELETE INTERVIEW
    // =========================================================

    public void deleteInterview(
            Integer interviewId) {

        Interview interview =
                getInterviewById(interviewId);

        interviewRepository.delete(interview);
    }

    // =========================================================
    // SEND INTERVIEW NOTIFICATION
    // =========================================================

    private void sendInterviewNotification(
            Integer applicationId,
            String message) {

        Application application =
                applicationRepository
                        .findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"));

        sendInterviewNotification(
                application,
                message);
    }

    // =========================================================
    // SEND NOTIFICATION USING APPLICATION
    // =========================================================

    private void sendInterviewNotification(
            Application application,
            String message) {

        if (application == null) {
            throw new RuntimeException(
                    "Application is required");
        }

        // -----------------------------------------------------
        // FIND JOB SEEKER
        // -----------------------------------------------------

        JobSeeker jobSeeker =
                jobSeekerRepository
                        .findById(
                                application.getSeekerId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job seeker not found"));

        // -----------------------------------------------------
        // GET ACTUAL USER ID
        // -----------------------------------------------------
        // seekerId and userId are different IDs.
        // Notification must use userId.
        // -----------------------------------------------------

        Integer userId =
                jobSeeker.getUserId();

        if (userId == null) {
            throw new RuntimeException(
                    "User ID not found for job seeker");
        }

        // -----------------------------------------------------
        // CREATE NOTIFICATION
        // -----------------------------------------------------

        notificationService.createNotification(
                userId,
                message
        );
    }

    // =========================================================
    // BUILD SCHEDULED INTERVIEW MESSAGE
    // =========================================================

    private String buildScheduledMessage(
            Interview interview) {

        StringBuilder message =
                new StringBuilder();

        message.append(
                "Your interview has been scheduled.");

        // -----------------------------------------------------
        // DATE
        // -----------------------------------------------------

        if (interview.getInterviewDate() != null) {

            message.append(" Date: ")
                    .append(interview.getInterviewDate());
        }

        // -----------------------------------------------------
        // INTERVIEW TYPE
        // -----------------------------------------------------

        if (interview.getInterviewType() != null) {

            message.append(". Type: ")
                    .append(
                            formatInterviewType(
                                    interview.getInterviewType())
                    );
        }

        // -----------------------------------------------------
        // ONLINE MEETING LINK
        // -----------------------------------------------------

        if (interview.getInterviewType()
                == InterviewType.ONLINE
                && interview.getMeetingLink() != null) {

            message.append(". Meeting link: ")
                    .append(interview.getMeetingLink());
        }

        // -----------------------------------------------------
        // OFFLINE LOCATION
        // -----------------------------------------------------

        if (interview.getInterviewType()
                == InterviewType.OFFLINE
                && interview.getLocation() != null) {

            message.append(". Location: ")
                    .append(interview.getLocation());
        }

        // -----------------------------------------------------
        // PHONE
        // -----------------------------------------------------

        if (interview.getInterviewType()
                == InterviewType.PHONE) {

            message.append(
                    ". The recruiter will contact you by phone.");
        }

        return message.toString();
    }

    // =========================================================
    // FORMAT INTERVIEW TYPE
    // =========================================================

    private String formatInterviewType(
            InterviewType type) {

        if (type == null) {
            return "Interview";
        }

        switch (type) {

            case ONLINE:
                return "Online";

            case OFFLINE:
                return "Offline";

            case PHONE:
                return "Phone";

            default:
                return type.name();
        }
    }
}