package com.Hirehub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "saved_jobs")
public class SavedJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saved_id")
    private Integer savedId;

    @Column(name = "seeker_id", nullable = false)
    private Integer seekerId;

    @Column(name = "job_id", nullable = false)
    private Integer jobId;

    @Column(name = "saved_at")
    private LocalDateTime savedAt = LocalDateTime.now();

    public SavedJob() {
    }

    public Integer getSavedId() {
        return savedId;
    }

    public void setSavedId(Integer savedId) {
        this.savedId = savedId;
    }

    public Integer getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(Integer seekerId) {
        this.seekerId = seekerId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}