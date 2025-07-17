package com.habitstack.service;

import com.habitstack.model.Habit;
import com.habitstack.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitService {
    
    @Autowired
    private HabitRepository habitRepository;
    
    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }
    
    public Optional<Habit> getHabitById(String id) {
        return habitRepository.findById(id);
    }
    
    public List<Habit> getHabitsByCategory(String category) {
        return habitRepository.findByCategory(category);
    }
    
    public List<Habit> searchHabits(String query) {
        return habitRepository.findByNameContainingIgnoreCase(query);
    }
    
    public List<Habit> searchHabitsByCategory(String category, String query) {
        return habitRepository.findByCategoryAndNameContainingIgnoreCase(category, query);
    }
    
    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }
    
    public Habit updateHabit(String id, Habit habitDetails) {
        Optional<Habit> optionalHabit = habitRepository.findById(id);
        if (optionalHabit.isPresent()) {
            Habit habit = optionalHabit.get();
            habit.setName(habitDetails.getName());
            habit.setCategory(habitDetails.getCategory());
            habit.setTime(habitDetails.getTime());
            habit.setDescription(habitDetails.getDescription());
            return habitRepository.save(habit);
        }
        return null;
    }
    
    public boolean deleteHabit(String id) {
        if (habitRepository.existsById(id)) {
            habitRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean existsByName(String name) {
        return habitRepository.existsByName(name);
    }
}