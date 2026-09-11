
package com.Hirehub.repository;

import com.Hirehub.entity.JobSkill;
import com.Hirehub.entity.JobSkillId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository
        extends JpaRepository<JobSkill, JobSkillId> {

    List<JobSkill> findByJobId(Integer jobId);

    boolean existsByJobIdAndSkillId(
            Integer jobId,
            Integer skillId);

    void deleteByJobIdAndSkillId(
            Integer jobId,
            Integer skillId);
}

