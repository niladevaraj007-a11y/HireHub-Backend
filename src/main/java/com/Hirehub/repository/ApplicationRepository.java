package com.Hirehub.repository;

import com.Hirehub.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository
        extends JpaRepository<Application, Integer> {

    List<Application> findBySeekerId(Integer seekerId);

    List<Application> findByJobId(Integer jobId);

    boolean existsByJobIdAndSeekerId(
            Integer jobId,
            Integer seekerId);
}