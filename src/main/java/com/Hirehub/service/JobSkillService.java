package com.Hirehub.service;

import com.Hirehub.entity.JobSkill;
import com.Hirehub.entity.JobSkill.Importance;
import com.Hirehub.entity.JobSkillId;
import com.Hirehub.repository.JobSkillRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobSkillService {

    private final JobSkillRepository repository;

    public JobSkillService(
            JobSkillRepository repository) {

        this.repository = repository;
    }

    // =========================================
    // ADD SKILL TO JOB
    // =========================================

    public JobSkill addSkill(
            Integer jobId,
            Integer skillId,
            Importance importance) {

        if (repository.existsByJobIdAndSkillId(
                jobId,
                skillId)) {

            throw new RuntimeException(
                    "Skill already added to job");
        }

        JobSkill jobSkill =
                new JobSkill();

        jobSkill.setJobId(jobId);
        jobSkill.setSkillId(skillId);

        if (importance == null) {
            importance = Importance.REQUIRED;
        }

        jobSkill.setImportance(importance);

        return repository.save(jobSkill);
    }

    // =========================================
    // GET ALL SKILLS OF JOB
    // =========================================

    public List<JobSkill> getSkillsByJob(
            Integer jobId) {

        return repository.findByJobId(jobId);
    }

    // =========================================
    // UPDATE IMPORTANCE
    // =========================================

    public JobSkill updateImportance(
            Integer jobId,
            Integer skillId,
            Importance importance) {

        JobSkillId id =
                new JobSkillId(
                        jobId,
                        skillId);

        JobSkill jobSkill =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job skill not found"));

        if (importance == null) {
            throw new RuntimeException(
                    "Importance is required");
        }

        jobSkill.setImportance(importance);

        return repository.save(jobSkill);
    }

    // =========================================
    // REMOVE SKILL FROM JOB
    // =========================================

    @Transactional
    public void removeSkill(
            Integer jobId,
            Integer skillId) {

        if (!repository.existsByJobIdAndSkillId(
                jobId,
                skillId)) {

            throw new RuntimeException(
                    "Job skill not found");
        }

        repository.deleteByJobIdAndSkillId(
                jobId,
                skillId);
    }
}