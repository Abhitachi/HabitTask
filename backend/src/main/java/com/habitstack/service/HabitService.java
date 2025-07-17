package com.habitstack.service;

import com.habitstack.exception.DatabaseException;
import com.habitstack.exception.ResourceNotFoundException;
import com.habitstack.model.Habit;
import com.habitstack.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitService {
    
    private final HabitRepository habitRepository;
    
    public List<Habit> getAllHabits() {
        log.info("Fetching all habits");
        try {
            List<Habit> habits = habitRepository.findAll();
            log.info("Successfully retrieved {} habits", habits.size());
            return habits;
        } catch (DataAccessException e) {
            log.error("Database error while fetching habits: {}", e.getMessage(), e);
            throw new DatabaseException("Failed to fetch habits", e);
        }
    }
    
    public Habit getHabitById(String id) {
        log.info("Fetching habit with ID: {}", id);
        try {
            return habitRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Habit not found with ID: {}", id);
                        return new ResourceNotFoundException("Habit", id);
                    });
        } catch (DataAccessException e) {
            log.error("Database error while fetching habit with ID {}: {}", id, e.getMessage(), e);
            throw new DatabaseException("Failed to fetch habit", e);
        }
    }
    
    public List<Habit> getHabitsByCategory(String category) {
        log.info("Fetching habits by category: {}", category);
        try {
            List<Habit> habits = habitRepository.findByCategory(category);
            log.info("Successfully retrieved {} habits for category: {}", habits.size(), category);
            return habits;
        } catch (DataAccessException e) {
            log.error("Database error while fetching habits by category {}: {}", category, e.getMessage(), e);
            throw new DatabaseException("Failed to fetch habits by category", e);
        }
    }
    
    public List<Habit> searchHabits(String query) {
        log.info("Searching habits with query: {}", query);
        try {
            List<Habit> habits = habitRepository.findByNameContainingIgnoreCase(query);
            log.info("Found {} habits matching query: {}", habits.size(), query);
            return habits;
        } catch (DataAccessException e) {
            log.error("Database error while searching habits with query {}: {}", query, e.getMessage(), e);
            throw new DatabaseException("Failed to search habits", e);
        }
    }
    
    public List<Habit> searchHabitsByCategory(String category, String query) {
        log.info("Searching habits by category: {} with query: {}", category, query);
        try {
            List<Habit> habits = habitRepository.findByCategoryAndNameContainingIgnoreCase(category, query);
            log.info("Found {} habits for category {} matching query: {}", habits.size(), category, query);
            return habits;
        } catch (DataAccessException e) {
            log.error("Database error while searching habits by category {} with query {}: {}", 
                    category, query, e.getMessage(), e);
            throw new DatabaseException("Failed to search habits by category", e);
        }
    }
    
    public Habit createHabit(Habit habit) {
        log.info("Creating new habit: {}", habit.getName());
        try {
            Habit savedHabit = habitRepository.save(habit);
            log.info("Successfully created habit with ID: {}", savedHabit.getId());
            return savedHabit;
        } catch (DataAccessException e) {
            log.error("Database error while creating habit {}: {}", habit.getName(), e.getMessage(), e);
            throw new DatabaseException("Failed to create habit", e);
        }
    }
    
    public Habit updateHabit(String id, Habit habitDetails) {
        log.info("Updating habit with ID: {}", id);
        try {
            Habit existingHabit = habitRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Habit not found for update with ID: {}", id);
                        return new ResourceNotFoundException("Habit", id);
                    });
            
            existingHabit.setName(habitDetails.getName());
            existingHabit.setCategory(habitDetails.getCategory());
            existingHabit.setTime(habitDetails.getTime());
            existingHabit.setDescription(habitDetails.getDescription());
            
            Habit updatedHabit = habitRepository.save(existingHabit);
            log.info("Successfully updated habit with ID: {}", id);
            return updatedHabit;
        } catch (DataAccessException e) {
            log.error("Database error while updating habit with ID {}: {}", id, e.getMessage(), e);
            throw new DatabaseException("Failed to update habit", e);
        }
    }
    
    public void deleteHabit(String id) {
        log.info("Deleting habit with ID: {}", id);
        try {
            if (!habitRepository.existsById(id)) {
                log.warn("Habit not found for deletion with ID: {}", id);
                throw new ResourceNotFoundException("Habit", id);
            }
            
            habitRepository.deleteById(id);
            log.info("Successfully deleted habit with ID: {}", id);
        } catch (DataAccessException e) {
            log.error("Database error while deleting habit with ID {}: {}", id, e.getMessage(), e);
            throw new DatabaseException("Failed to delete habit", e);
        }
    }
    
    public boolean existsByName(String name) {
        log.debug("Checking if habit exists with name: {}", name);
        try {
            boolean exists = habitRepository.existsByName(name);
            log.debug("Habit exists check for name {}: {}", name, exists);
            return exists;
        } catch (DataAccessException e) {
            log.error("Database error while checking habit existence with name {}: {}", name, e.getMessage(), e);
            throw new DatabaseException("Failed to check habit existence", e);
        }
    }
}