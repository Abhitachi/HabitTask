package com.habitstack.controller;

import com.habitstack.model.HabitStack;
import com.habitstack.service.HabitStackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/stacks")
@Validated
@CrossOrigin(origins = "*")
public class HabitStackController {
    
    @Autowired
    private HabitStackService stackService;
    
    @GetMapping
    public ResponseEntity<List<HabitStack>> getAllStacks(
            @RequestParam(required = false) String search) {
        
        List<HabitStack> stacks = search != null ? 
                stackService.searchStacks(search) : stackService.getAllStacks();
        
        return ResponseEntity.ok(stacks);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HabitStack> getStackById(@PathVariable String id) {
        Optional<HabitStack> stack = stackService.getStackById(id);
        return stack.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<HabitStack> createStack(@Valid @RequestBody HabitStack stack) {
        try {
            HabitStack createdStack = stackService.createStack(stack);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStack);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HabitStack> updateStack(@PathVariable String id, 
                                                @Valid @RequestBody HabitStack stackDetails) {
        try {
            HabitStack updatedStack = stackService.updateStack(id, stackDetails);
            return updatedStack != null ? ResponseEntity.ok(updatedStack) 
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStack(@PathVariable String id) {
        try {
            boolean deleted = stackService.deleteStack(id);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/toggle-habit")
    public ResponseEntity<Map<String, String>> toggleHabitCompletion(
            @RequestBody Map<String, String> request) {
        try {
            String stackId = request.get("stack_id");
            String habitId = request.get("habit_id");
            
            HabitStack updatedStack = stackService.toggleHabitCompletion(stackId, habitId);
            
            if (updatedStack != null) {
                return ResponseEntity.ok(Map.of("message", "Habit completion toggled successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to toggle habit completion"));
        }
    }
}