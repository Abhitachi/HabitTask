package com.habitstack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class StackHabit {
    @NotBlank(message = "Habit ID is required")
    @JsonProperty("habitId")
    private String habitId;
    
    private boolean completed = false;
    
    public StackHabit() {}
    
    public StackHabit(String habitId) {
        this.habitId = habitId;
        this.completed = false;
    }
    
    public StackHabit(String habitId, boolean completed) {
        this.habitId = habitId;
        this.completed = completed;
    }
    
    // Getters and Setters
    public String getHabitId() { return habitId; }
    public void setHabitId(String habitId) { this.habitId = habitId; }
    
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}