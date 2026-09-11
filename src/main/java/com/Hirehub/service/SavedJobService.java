package com.Hirehub.service;

import com.Hirehub.entity.SavedJob;
import com.Hirehub.repository.SavedJobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SavedJobService {

    private final SavedJobRepository savedJobRepository;

    public SavedJobService(SavedJobRepository savedJobRepository) {
        this.savedJobRepository = savedJobRepository;
    }

    public SavedJob saveJob(Integer seekerId, Integer jobId) {

        if (savedJobRepository.findBySeekerIdAndJobId(seekerId, jobId).isPresent()) {
            throw new RuntimeException("Job already saved");
        }

        SavedJob savedJob = new SavedJob();
        savedJob.setSeekerId(seekerId);
        savedJob.setJobId(jobId);

        return savedJobRepository.save(savedJob);
    }

    public List<SavedJob> getSavedJobs(Integer seekerId) {
        return savedJobRepository.findBySeekerId(seekerId);
    }

    @Transactional
    public void removeSavedJob(Integer seekerId, Integer jobId) {

        if (savedJobRepository.findBySeekerIdAndJobId(seekerId, jobId).isEmpty()) {
            throw new RuntimeException("Saved job not found");
        }

        savedJobRepository.deleteBySeekerIdAndJobId(seekerId, jobId);
    }
}