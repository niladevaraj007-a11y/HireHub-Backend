package com.Hirehub.repository;

import com.Hirehub.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {

    List<Job> findByStatus(Job.JobStatus status);

    List<Job> findByLocationIgnoreCase(String location);

    List<Job> findByCompanyId(Integer companyId);
}