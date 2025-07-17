package com.habitstack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StackHabit {
    @NotBlank(message = "Habit ID is required")
    @JsonProperty("habitId")
    private String habitId;
    
    @Builder.Default
    private boolean completed = false;
    
    public StackHabit(String habitId) {
        this.habitId = habitId;
        this.completed = false;
    }
}