
package com.Hirehub.service;

import com.Hirehub.entity.Company;
import com.Hirehub.entity.Recruiter;
import com.Hirehub.repository.CompanyRepository;
import com.Hirehub.repository.RecruiterRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final RecruiterRepository recruiterRepository;

    public CompanyService(
            CompanyRepository companyRepository,
            RecruiterRepository recruiterRepository) {

        this.companyRepository = companyRepository;
        this.recruiterRepository = recruiterRepository;
    }

    // =========================================================
    // GET ALL COMPANIES
    // =========================================================

    public List<Company> getAllCompanies() {

        return companyRepository.findAll();
    }

    // =========================================================
    // GET COMPANY
    // =========================================================

    public Company getCompanyById(Integer companyId) {

        return companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Company not found"
                        )
                );
    }

    // =========================================================
    // CREATE COMPANY FOR RECRUITER
    // =========================================================

    @Transactional
    public Company createCompany(
            Integer recruiterId,
            Company company) {

        if (recruiterId == null) {

            throw new RuntimeException(
                    "Recruiter ID is required"
            );
        }

        if (company.getCompanyName() == null ||
                company.getCompanyName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Company name is required"
            );
        }

        Recruiter recruiter =
                recruiterRepository.findById(recruiterId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter not found"
                                )
                        );

        // Check if recruiter already has company

        if (recruiter.getCompanyId() != null) {

            throw new RuntimeException(
                    "Recruiter already has a company"
            );
        }

        // -----------------------------------------------------
        // Step 1: Save company without recruiter ID
        // -----------------------------------------------------

        company.setRecruiterId(null);

        Company savedCompany =
                companyRepository.save(company);

        // -----------------------------------------------------
        // Step 2: Connect recruiter to company
        // -----------------------------------------------------

        recruiter.setCompanyId(
                savedCompany.getCompanyId()
        );

        recruiterRepository.save(recruiter);

        // -----------------------------------------------------
        // Step 3: Connect company back to recruiter
        // -----------------------------------------------------

        savedCompany.setRecruiterId(
                recruiter.getRecruiterId()
        );

        savedCompany =
                companyRepository.save(savedCompany);

        return savedCompany;
    }

    // =========================================================
    // UPDATE COMPANY
    // =========================================================

    @Transactional
    public Company updateCompany(
            Integer recruiterId,
            Integer companyId,
            Company company) {

        if (recruiterId == null) {

            throw new RuntimeException(
                    "Recruiter ID is required"
            );
        }

        if (companyId == null) {

            throw new RuntimeException(
                    "Company ID is required"
            );
        }

        if (company.getCompanyName() == null ||
                company.getCompanyName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Company name is required"
            );
        }

        // -----------------------------------------------------
        // Find recruiter
        // -----------------------------------------------------

        Recruiter recruiter =
                recruiterRepository.findById(recruiterId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter not found"
                                )
                        );

        // -----------------------------------------------------
        // Check recruiter owns this company
        // -----------------------------------------------------

        if (recruiter.getCompanyId() == null ||
                !recruiter.getCompanyId().equals(companyId)) {

            throw new RuntimeException(
                    "You are not authorized to update this company"
            );
        }

        // -----------------------------------------------------
        // Find company
        // -----------------------------------------------------

        Company existingCompany =
                companyRepository.findById(companyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found"
                                )
                        );

        // -----------------------------------------------------
        // Double-check ownership
        // -----------------------------------------------------

        if (existingCompany.getRecruiterId() == null ||
                !existingCompany.getRecruiterId().equals(recruiterId)) {

            throw new RuntimeException(
                    "You are not authorized to update this company"
            );
        }

        // -----------------------------------------------------
        // Update allowed company fields
        // -----------------------------------------------------

        existingCompany.setCompanyName(
                company.getCompanyName().trim()
        );

        existingCompany.setDescription(
                company.getDescription()
        );

        existingCompany.setWebsite(
                company.getWebsite()
        );

        existingCompany.setLocation(
                company.getLocation()
        );

        existingCompany.setCompanySize(
                company.getCompanySize()
        );

        // Keep original ownership

        existingCompany.setRecruiterId(
                recruiterId
        );

        return companyRepository.save(existingCompany);
    }
}

