package com.Hirehub.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "job_seekers")
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seeker_id")
    private Integer seekerId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Integer userId;

    @Column(length = 150)
    private String headline;

    @Column(columnDefinition = "TEXT")
    private String about;

    @Column(length = 150)
    private String education;

    @Column(name = "experience_years", precision = 3, scale = 1)
    private BigDecimal experienceYears;

    @Column(length = 100)
    private String location;

    public JobSeeker() {
    }

    public Integer getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(Integer seekerId) {
        this.seekerId = seekerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public BigDecimal getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(BigDecimal experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}