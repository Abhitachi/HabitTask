package com.habitstack.controller;

import com.habitstack.model.ProgressData;
import com.habitstack.service.ProgressDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://chain-habits.preview.emergentagent.com")
@RequestMapping("/progress")
@Validated

public class ProgressDataController {
    
    @Autowired
    private ProgressDataService progressService;
    
    @GetMapping
    public ResponseEntity<List<ProgressData>> getAllProgress() {
        List<ProgressData> progressList = progressService.getAllProgress();
        return ResponseEntity.ok(progressList);
    }
    
    @GetMapping("/{stackId}")
    public ResponseEntity<ProgressData> getProgressByStackId(@PathVariable String stackId) {
        Optional<ProgressData> progress = progressService.getProgressByStackId(stackId);
        
        if (progress.isPresent()) {
            return ResponseEntity.ok(progress.get());
        } else {
            // Create default progress data if it doesn't exist
            ProgressData defaultProgress = new ProgressData(stackId);
            ProgressData createdProgress = progressService.createOrUpdateProgress(defaultProgress);
            return ResponseEntity.ok(createdProgress);
        }
    }
    
    @PutMapping("/{stackId}")
    public ResponseEntity<ProgressData> updateProgress(@PathVariable String stackId,
                                                      @Valid @RequestBody ProgressData progressDetails) {
        try {
            ProgressData updatedProgress = progressService.updateProgress(stackId, progressDetails);
            return updatedProgress != null ? ResponseEntity.ok(updatedProgress) 
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable String id) {
        try {
            boolean deleted = progressService.deleteProgress(id);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}