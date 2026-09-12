
package com.Hirehub.controller;

import com.Hirehub.entity.Company;
import com.Hirehub.service.CompanyService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // =========================================================
    // GET ALL COMPANIES
    // =========================================================

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {

        return ResponseEntity.ok(
                companyService.getAllCompanies()
        );
    }

    // =========================================================
    // GET COMPANY BY ID
    // =========================================================

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompanyById(
            @PathVariable Integer companyId) {

        return ResponseEntity.ok(
                companyService.getCompanyById(companyId)
        );
    }

    // =========================================================
    // CREATE COMPANY FOR RECRUITER
    // =========================================================

    @PostMapping("/recruiter/{recruiterId}")
    public ResponseEntity<Company> createCompany(
            @PathVariable Integer recruiterId,
            @RequestBody Company company) {

        return ResponseEntity.ok(
                companyService.createCompany(
                        recruiterId,
                        company
                )
        );
    }

    // =========================================================
    // UPDATE COMPANY
    // =========================================================

    @PutMapping("/recruiter/{recruiterId}/{companyId}")
    public ResponseEntity<Company> updateCompany(
            @PathVariable Integer recruiterId,
            @PathVariable Integer companyId,
            @RequestBody Company company) {

        return ResponseEntity.ok(
                companyService.updateCompany(
                        recruiterId,
                        companyId,
                        company
                )
        );
    }
}

