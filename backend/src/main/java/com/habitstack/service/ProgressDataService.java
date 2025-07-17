package com.habitstack.service;

import com.habitstack.model.ProgressData;
import com.habitstack.repository.ProgressDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressDataService {
    
    @Autowired
    private ProgressDataRepository progressRepository;
    
    public List<ProgressData> getAllProgress() {
        return progressRepository.findAll();
    }
    
    public Optional<ProgressData> getProgressById(String id) {
        return progressRepository.findById(id);
    }
    
    public Optional<ProgressData> getProgressByStackId(String stackId) {
        return progressRepository.findByStackId(stackId);
    }
    
    public ProgressData createOrUpdateProgress(ProgressData progressData) {
        Optional<ProgressData> existingProgress = progressRepository.findByStackId(progressData.getStackId());
        
        if (existingProgress.isPresent()) {
            ProgressData existing = existingProgress.get();
            existing.setCurrentStreak(progressData.getCurrentStreak());
            existing.setLongestStreak(progressData.getLongestStreak());
            existing.setCompletionRate(progressData.getCompletionRate());
            existing.setLastWeekProgress(progressData.getLastWeekProgress());
            existing.setUpdatedAt(LocalDateTime.now());
            return progressRepository.save(existing);
        } else {
            progressData.setUpdatedAt(LocalDateTime.now());
            return progressRepository.save(progressData);
        }
    }
    
    public ProgressData updateProgress(String stackId, ProgressData progressDetails) {
        Optional<ProgressData> optionalProgress = progressRepository.findByStackId(stackId);
        if (optionalProgress.isPresent()) {
            ProgressData progress = optionalProgress.get();
            progress.setCurrentStreak(progressDetails.getCurrentStreak());
            progress.setLongestStreak(progressDetails.getLongestStreak());
            progress.setCompletionRate(progressDetails.getCompletionRate());
            progress.setLastWeekProgress(progressDetails.getLastWeekProgress());
            progress.setUpdatedAt(LocalDateTime.now());
            return progressRepository.save(progress);
        }
        return null;
    }
    
    public boolean deleteProgress(String id) {
        if (progressRepository.existsById(id)) {
            progressRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public void deleteProgressByStackId(String stackId) {
        progressRepository.deleteByStackId(stackId);
    }
}