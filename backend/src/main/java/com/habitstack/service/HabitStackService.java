package com.habitstack.service;

import com.habitstack.model.HabitStack;
import com.habitstack.model.StackHabit;
import com.habitstack.model.ProgressData;
import com.habitstack.repository.HabitStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HabitStackService {
    
    @Autowired
    private HabitStackRepository habitStackRepository;
    
    @Autowired
    private ProgressDataService progressDataService;
    
    public List<HabitStack> getAllStacks() {
        return habitStackRepository.findAll();
    }
    
    public Optional<HabitStack> getStackById(String id) {
        return habitStackRepository.findById(id);
    }
    
    public List<HabitStack> searchStacks(String query) {
        return habitStackRepository.findByNameContainingIgnoreCase(query);
    }
    
    public HabitStack createStack(HabitStack stack) {
        HabitStack savedStack = habitStackRepository.save(stack);
        
        // Create initial progress data
        ProgressData progressData = new ProgressData(savedStack.getId());
        progressDataService.createOrUpdateProgress(progressData);
        
        return savedStack;
    }
    
    public HabitStack updateStack(String id, HabitStack stackDetails) {
        Optional<HabitStack> optionalStack = habitStackRepository.findById(id);
        if (optionalStack.isPresent()) {
            HabitStack stack = optionalStack.get();
            stack.setName(stackDetails.getName());
            stack.setHabits(stackDetails.getHabits());
            stack.setLastCompleted(stackDetails.getLastCompleted());
            return habitStackRepository.save(stack);
        }
        return null;
    }
    
    public boolean deleteStack(String id) {
        if (habitStackRepository.existsById(id)) {
            habitStackRepository.deleteById(id);
            progressDataService.deleteProgressByStackId(id);
            return true;
        }
        return false;
    }
    
    public HabitStack toggleHabitCompletion(String stackId, String habitId) {
        Optional<HabitStack> optionalStack = habitStackRepository.findById(stackId);
        if (optionalStack.isPresent()) {
            HabitStack stack = optionalStack.get();
            
            // Find and toggle the habit
            for (StackHabit stackHabit : stack.getHabits()) {
                if (stackHabit.getHabitId().equals(habitId)) {
                    stackHabit.setCompleted(!stackHabit.isCompleted());
                    break;
                }
            }
            
            // Update last completed if all habits are completed
            if (stack.isCompleted()) {
                stack.setLastCompleted(LocalDateTime.now());
            }
            
            HabitStack updatedStack = habitStackRepository.save(stack);
            
            // Update progress data
            updateProgressData(updatedStack);
            
            return updatedStack;
        }
        return null;
    }
    
    private void updateProgressData(HabitStack stack) {
        ProgressData progressData = progressDataService.getProgressByStackId(stack.getId())
                .orElse(new ProgressData(stack.getId()));
        
        double completionRate = stack.getCompletionPercentage() / 100.0;
        progressData.setCompletionRate(completionRate);
        
        // Update streak if stack is completed
        if (stack.isCompleted()) {
            progressData.setCurrentStreak(progressData.getCurrentStreak() + 1);
            progressData.setLongestStreak(Math.max(progressData.getLongestStreak(), progressData.getCurrentStreak()));
        }
        
        progressData.setUpdatedAt(LocalDateTime.now());
        progressDataService.createOrUpdateProgress(progressData);
    }
    
    public boolean existsByName(String name) {
        return habitStackRepository.existsByName(name);
    }
}