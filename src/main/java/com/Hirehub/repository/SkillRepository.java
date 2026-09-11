package com.Hirehub.repository;

import com.Hirehub.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository
        extends JpaRepository<Skill, Integer> {

    Optional<Skill> findBySkillName(String skillName);

    boolean existsBySkillName(String skillName);
}