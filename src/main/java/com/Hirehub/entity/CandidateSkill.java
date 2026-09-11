package com.Hirehub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate_skills")
@IdClass(CandidateSkillId.class)
public class CandidateSkill {

    @Id
    @Column(name = "seeker_id")
    private Integer seekerId;

    @Id
    @Column(name = "skill_id")
    private Integer skillId;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level")
    private SkillLevel skillLevel;

    public CandidateSkill() {
    }

    public Integer getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(Integer seekerId) {
        this.seekerId = seekerId;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    public enum SkillLevel {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED
    }
}