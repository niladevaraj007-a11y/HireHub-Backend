package com.Hirehub.repository;

import com.Hirehub.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedJobRepository extends JpaRepository<SavedJob, Integer> {

    List<SavedJob> findBySeekerId(Integer seekerId);

    Optional<SavedJob> findBySeekerIdAndJobId(Integer seekerId, Integer jobId);

    void deleteBySeekerIdAndJobId(Integer seekerId, Integer jobId);
}