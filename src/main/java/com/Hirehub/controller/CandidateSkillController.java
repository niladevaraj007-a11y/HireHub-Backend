package com.Hirehub.controller;

import com.Hirehub.entity.CandidateSkill;
import com.Hirehub.entity.CandidateSkill.SkillLevel;
import com.Hirehub.service.CandidateSkillService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate-skills")
@CrossOrigin
public class CandidateSkillController {

    private final CandidateSkillService candidateSkillService;

    public CandidateSkillController(
            CandidateSkillService candidateSkillService) {

        this.candidateSkillService =
                candidateSkillService;
    }

    // =========================================
    // GET ALL SKILLS OF A SEEKER
    // =========================================

    @GetMapping("/seeker/{seekerId}")
    public ResponseEntity<List<CandidateSkill>> getSkillsBySeeker(
            @PathVariable Integer seekerId) {

        return ResponseEntity.ok(
                candidateSkillService
                        .getSkillsBySeeker(seekerId));
    }

    // =========================================
    // ADD SKILL TO SEEKER
    // =========================================
    //
    // POST
    // /api/candidate-skills/add
    // ?seekerId=2&skillId=5&skillLevel=INTERMEDIATE
    //

    @PostMapping("/add")
    public ResponseEntity<CandidateSkill> addSkill(
            @RequestParam Integer seekerId,
            @RequestParam Integer skillId,
            @RequestParam SkillLevel skillLevel) {

        return ResponseEntity.ok(
                candidateSkillService.addSkill(
                        seekerId,
                        skillId,
                        skillLevel));
    }

    // =========================================
    // UPDATE SKILL LEVEL
    // =========================================
    //
    // PUT
    // /api/candidate-skills/update
    // ?seekerId=2&skillId=5&skillLevel=ADVANCED
    //

    @PutMapping("/update")
    public ResponseEntity<CandidateSkill> updateSkillLevel(
            @RequestParam Integer seekerId,
            @RequestParam Integer skillId,
            @RequestParam SkillLevel skillLevel) {

        return ResponseEntity.ok(
                candidateSkillService.updateSkillLevel(
                        seekerId,
                        skillId,
                        skillLevel));
    }

    // =========================================
    // REMOVE SKILL
    // =========================================
    //
    // DELETE
    // /api/candidate-skills/remove
    // ?seekerId=2&skillId=5
    //

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeSkill(
            @RequestParam Integer seekerId,
            @RequestParam Integer skillId) {

        candidateSkillService.removeSkill(
                seekerId,
                skillId);

        return ResponseEntity.ok(
                "Candidate skill removed successfully");
    }
}