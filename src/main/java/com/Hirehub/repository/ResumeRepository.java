package com.Hirehub.repository;

import com.Hirehub.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    List<Resume> findBySeekerId(Integer seekerId);
}