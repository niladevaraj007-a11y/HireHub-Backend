package com.Hirehub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "job_skills")
@IdClass(JobSkillId.class)
public class JobSkill {

    @Id
    @Column(name = "job_id")
    private Integer jobId;

    @Id
    @Column(name = "skill_id")
    private Integer skillId;

    @Enumerated(EnumType.STRING)
    @Column(name = "importance")
    private Importance importance;

    public JobSkill() {
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public enum Importance {
        REQUIRED,
        PREFERRED
    }
}