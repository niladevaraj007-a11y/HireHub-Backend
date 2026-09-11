package com.Hirehub.repository;

import com.Hirehub.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository
        extends JpaRepository<Interview, Integer> {

    List<Interview> findByApplicationId(Integer applicationId);

}