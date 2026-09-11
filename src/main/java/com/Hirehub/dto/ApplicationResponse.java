package com.Hirehub.dto;

import java.time.LocalDateTime;

public class ApplicationResponse {

    private Integer applicationId;

    private Integer jobId;

    private Integer seekerId;

    private Integer resumeId;

    private String jobTitle;

    private String companyName;

    private String location;

    private String status;

    private LocalDateTime appliedAt;

    public ApplicationResponse() {
    }

    public ApplicationResponse(
            Integer applicationId,
            Integer jobId,
            Integer seekerId,
            Integer resumeId,
            String jobTitle,
            String companyName,
            String location,
            String status,
            LocalDateTime appliedAt) {

        this.applicationId = applicationId;
        this.jobId = jobId;
        this.seekerId = seekerId;
        this.resumeId = resumeId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(Integer seekerId) {
        this.seekerId = seekerId;
    }

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}