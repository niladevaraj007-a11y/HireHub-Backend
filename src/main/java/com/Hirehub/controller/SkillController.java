package com.Hirehub.controller;

import com.Hirehub.entity.Skill;
import com.Hirehub.service.SkillService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin
public class SkillController {

    private final SkillService skillService;

    public SkillController(
            SkillService skillService) {

        this.skillService = skillService;
    }

    // GET ALL SKILLS
    // GET /api/skills
    @GetMapping
    public List<Skill> getAllSkills() {

        return skillService.getAllSkills();
    }

    // GET SKILL BY ID
    // GET /api/skills/1
    @GetMapping("/{skillId}")
    public Skill getSkillById(
            @PathVariable Integer skillId) {

        return skillService.getSkillById(
                skillId);
    }

    // CREATE SKILL
    // POST /api/skills?skillName=Angular
    @PostMapping
    public Skill createSkill(
            @RequestParam String skillName) {

        return skillService.createSkill(
                skillName);
    }

    // DELETE SKILL
    // DELETE /api/skills/16
    @DeleteMapping("/{skillId}")
    public String deleteSkill(
            @PathVariable Integer skillId) {

        return skillService.deleteSkill(
                skillId);
    }
}