
package com.Hirehub.entity;

import java.io.Serializable;
import java.util.Objects;

public class CandidateSkillId implements Serializable {

    private Integer seekerId;
    private Integer skillId;

    public CandidateSkillId() {
    }

    public CandidateSkillId(Integer seekerId, Integer skillId) {
        this.seekerId = seekerId;
        this.skillId = skillId;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof CandidateSkillId)) {
            return false;
        }

        CandidateSkillId that = (CandidateSkillId) o;

        return Objects.equals(seekerId, that.seekerId)
                && Objects.equals(skillId, that.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seekerId, skillId);
    }
}

