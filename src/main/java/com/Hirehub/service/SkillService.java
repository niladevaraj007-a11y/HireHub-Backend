package com.Hirehub.service;

import com.Hirehub.entity.Skill;
import com.Hirehub.repository.SkillRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(
            SkillRepository skillRepository) {

        this.skillRepository = skillRepository;
    }

    // Get all skills
    public List<Skill> getAllSkills() {

        return skillRepository.findAll();
    }

    // Get skill by ID
    public Skill getSkillById(Integer skillId) {

        return skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Skill not found"));
    }

    // Create skill
    public Skill createSkill(String skillName) {

        if (skillName == null ||
                skillName.trim().isEmpty()) {

            throw new RuntimeException(
                    "Skill name is required");
        }

        skillName = skillName.trim();

        if (skillRepository
                .existsBySkillName(skillName)) {

            throw new RuntimeException(
                    "Skill already exists");
        }

        Skill skill = new Skill();

        skill.setSkillName(skillName);

        return skillRepository.save(skill);
    }

    // Delete skill
    public String deleteSkill(Integer skillId) {

        Skill skill =
                skillRepository.findById(skillId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Skill not found"));

        skillRepository.delete(skill);

        return "Skill deleted successfully";
    }
}