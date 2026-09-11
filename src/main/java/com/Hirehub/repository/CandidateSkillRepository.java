package com.Hirehub.repository;

import com.Hirehub.entity.CandidateSkill;
import com.Hirehub.entity.CandidateSkillId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateSkillRepository
        extends JpaRepository<CandidateSkill, CandidateSkillId> {

    List<CandidateSkill> findBySeekerId(
            Integer seekerId);

    boolean existsBySeekerIdAndSkillId(
            Integer seekerId,
            Integer skillId);

    void deleteBySeekerIdAndSkillId(
            Integer seekerId,
            Integer skillId);
}