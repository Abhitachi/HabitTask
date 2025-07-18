package com.habitstack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "habit_stacks")
public class HabitStack {
    @Id
    private String id;
    
    @NotBlank(message = "Stack name is required")
    private String name;
    
    @NotEmpty(message = "Stack must contain at least one habit")
    @Builder.Default
    private List<StackHabit> habits = new ArrayList<>();
    
    @Field("created_at")
    @JsonProperty("created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Field("last_completed")
    @JsonProperty("lastCompleted")
    private LocalDateTime lastCompleted;
    
    public HabitStack(String name, List<StackHabit> habits) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.habits = habits != null ? habits : new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }
    
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