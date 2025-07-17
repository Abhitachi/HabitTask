package com.habitstack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "habit_stacks")
public class HabitStack {
    @Id
    private String id;
    
    @NotBlank(message = "Stack name is required")
    private String name;
    
    @NotEmpty(message = "Stack must contain at least one habit")
    private List<StackHabit> habits;
    
    @Field("created_at")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    @Field("last_completed")
    @JsonProperty("lastCompleted")
    private LocalDateTime lastCompleted;
    
    public HabitStack() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.habits = new ArrayList<>();
    }
    
    public HabitStack(String name, List<StackHabit> habits) {
        this();
        this.name = name;
        this.habits = habits != null ? habits : new ArrayList<>();
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<StackHabit> getHabits() { return habits; }
    public void setHabits(List<StackHabit> habits) { this.habits = habits; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getLastCompleted() { return lastCompleted; }
    public void setLastCompleted(LocalDateTime lastCompleted) { this.lastCompleted = lastCompleted; }
    
    // Helper methods
    public boolean isCompleted() {
        return habits.stream().allMatch(StackHabit::isCompleted);
    }
    
    public int getCompletedCount() {
        return (int) habits.stream().filter(StackHabit::isCompleted).count();
    }
    
    public double getCompletionPercentage() {
        if (habits.isEmpty()) return 0.0;
        return (double) getCompletedCount() / habits.size() * 100;
    }
}