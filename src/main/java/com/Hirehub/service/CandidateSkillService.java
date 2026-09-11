package com.Hirehub.service;

import com.Hirehub.entity.CandidateSkill;
import com.Hirehub.entity.CandidateSkill.SkillLevel;
import com.Hirehub.entity.CandidateSkillId;
import com.Hirehub.repository.CandidateSkillRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CandidateSkillService {

    private final CandidateSkillRepository repository;

    public CandidateSkillService(
            CandidateSkillRepository repository) {

        this.repository = repository;
    }

    // =========================================
    // ADD SKILL TO CANDIDATE
    // =========================================

    public CandidateSkill addSkill(
            Integer seekerId,
            Integer skillId,
            SkillLevel skillLevel) {

        // Check duplicate skill
        if (repository.existsBySeekerIdAndSkillId(
                seekerId,
                skillId)) {

            throw new RuntimeException(
                    "Skill already added to candidate");
        }

        CandidateSkill candidateSkill =
                new CandidateSkill();

        candidateSkill.setSeekerId(seekerId);
        candidateSkill.setSkillId(skillId);
        candidateSkill.setSkillLevel(skillLevel);

        return repository.save(candidateSkill);
    }

    // =========================================
    // GET ALL SKILLS OF CANDIDATE
    // =========================================

    public List<CandidateSkill> getSkillsBySeeker(
            Integer seekerId) {

        return repository.findBySeekerId(
                seekerId);
    }

    // =========================================
    // REMOVE SKILL FROM CANDIDATE
    // =========================================

    @Transactional
    public void removeSkill(
            Integer seekerId,
            Integer skillId) {

        // Check skill exists
        if (!repository.existsBySeekerIdAndSkillId(
                seekerId,
                skillId)) {

            throw new RuntimeException(
                    "Candidate skill not found");
        }

        // Delete skill
        repository.deleteBySeekerIdAndSkillId(
                seekerId,
                skillId);
    }

    // =========================================
    // UPDATE SKILL LEVEL
    // =========================================

    public CandidateSkill updateSkillLevel(
            Integer seekerId,
            Integer skillId,
            SkillLevel skillLevel) {

        CandidateSkillId id =
                new CandidateSkillId(
                        seekerId,
                        skillId);

        CandidateSkill candidateSkill =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Candidate skill not found"));

        candidateSkill.setSkillLevel(
                skillLevel);

        return repository.save(candidateSkill);
    }
}