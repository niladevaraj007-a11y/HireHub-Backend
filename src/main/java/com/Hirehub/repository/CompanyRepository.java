
package com.Hirehub.repository;

import com.Hirehub.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository
        extends JpaRepository<Company, Integer> {
}

