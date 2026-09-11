
package com.Hirehub.controller;

import com.Hirehub.entity.JobSkill;
import com.Hirehub.entity.JobSkill.Importance;
import com.Hirehub.service.JobSkillService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-skills")
@CrossOrigin
public class JobSkillController {

    private final JobSkillService jobSkillService;

    public JobSkillController(
            JobSkillService jobSkillService) {

        this.jobSkillService = jobSkillService;
    }

    // =========================================
    // GET ALL SKILLS OF A JOB
    // =========================================
    //
    // GET
    // /api/job-skills/job/6
    //
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobSkill>> getSkillsByJob(
            @PathVariable Integer jobId) {

        return ResponseEntity.ok(
                jobSkillService.getSkillsByJob(jobId)
        );
    }

    // =========================================
    // ADD SKILL TO JOB
    // =========================================
    //
    // POST
    // /api/job-skills/add?jobId=6&skillId=5&importance=REQUIRED
    //
    @PostMapping("/add")
    public ResponseEntity<JobSkill> addSkill(
            @RequestParam Integer jobId,
            @RequestParam Integer skillId,
            @RequestParam(required = false) Importance importance) {

        return ResponseEntity.ok(
                jobSkillService.addSkill(
                        jobId,
                        skillId,
                        importance
                )
        );
    }

    // =========================================
    // UPDATE SKILL IMPORTANCE
    // =========================================
    //
    // PUT
    // /api/job-skills/update
    // ?jobId=6&skillId=5&importance=PREFERRED
    //
    @PutMapping("/update")
    public ResponseEntity<JobSkill> updateImportance(
            @RequestParam Integer jobId,
            @RequestParam Integer skillId,
            @RequestParam Importance importance) {

        return ResponseEntity.ok(
                jobSkillService.updateImportance(
                        jobId,
                        skillId,
                        importance
                )
        );
    }

    // =========================================
    // REMOVE SKILL FROM JOB
    // =========================================
    //
    // DELETE
    // /api/job-skills/remove?jobId=6&skillId=5
    //
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeSkill(
            @RequestParam Integer jobId,
            @RequestParam Integer skillId) {

        jobSkillService.removeSkill(
                jobId,
                skillId
        );

        return ResponseEntity.ok(
                "Job skill removed successfully"
        );
    }
}

