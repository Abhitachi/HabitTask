package com.habitstack.controller;

import com.habitstack.model.Habit;
import com.habitstack.service.HabitService;
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
@RequestMapping("/habits")
@Validated
public class HabitController {
    
    @Autowired
    private HabitService habitService;
    
    @GetMapping
    public ResponseEntity<List<Habit>> getAllHabits(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        
        List<Habit> habits;
        
        if (category != null && search != null) {
            habits = habitService.searchHabitsByCategory(category, search);
        } else if (category != null) {
            habits = habitService.getHabitsByCategory(category);
        } else if (search != null) {
            habits = habitService.searchHabits(search);
        } else {
            habits = habitService.getAllHabits();
        }
        System.out.println(habits + "habits test");
        return ResponseEntity.ok(habits);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Habit> getHabitById(@PathVariable String id) {
        try {
            Habit habit = habitService.getHabitById(id);
            return ResponseEntity.ok(habit);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Habit> createHabit(@Valid @RequestBody Habit habit) {
        try {
            Habit createdHabit = habitService.createHabit(habit);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHabit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Habit> updateHabit(@PathVariable String id, 
                                           @Valid @RequestBody Habit habitDetails) {
        try {
            Habit updatedHabit = habitService.updateHabit(id, habitDetails);
            return updatedHabit != null ? ResponseEntity.ok(updatedHabit) 
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable String id) {
        try {
            boolean deleted = habitService.deleteHabit(id);
            if(deleted){
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}