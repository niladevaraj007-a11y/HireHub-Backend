package com.Hirehub.repository;

import com.Hirehub.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruiterRepository
        extends JpaRepository<Recruiter, Integer> {

    Optional<Recruiter> findByUserId(Integer userId);
}