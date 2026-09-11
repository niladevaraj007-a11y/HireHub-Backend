package com.Hirehub.repository;

import com.Hirehub.entity.JobSeeker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobSeekerRepository
        extends JpaRepository<JobSeeker, Integer> {

    Optional<JobSeeker> findByUserId(Integer userId);

    List<JobSeeker> findByLocation(String location);
}