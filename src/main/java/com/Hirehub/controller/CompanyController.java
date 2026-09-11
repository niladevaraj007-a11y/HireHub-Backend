package com.Hirehub.controller;

import com.Hirehub.entity.Company;
import com.Hirehub.repository.CompanyRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin
public class CompanyController {

    private final CompanyRepository companyRepository;

    public CompanyController(
            CompanyRepository companyRepository) {

        this.companyRepository =
                companyRepository;
    }

    // GET ALL COMPANIES
    // GET /api/companies
    @GetMapping
    public List<Company> getAllCompanies() {

        return companyRepository.findAll();
    }

    // GET COMPANY BY ID
    // GET /api/companies/1
    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompanyById(
            @PathVariable Integer companyId) {

        Company company =
                companyRepository
                        .findById(companyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found"));

        return ResponseEntity.ok(company);
    }
}