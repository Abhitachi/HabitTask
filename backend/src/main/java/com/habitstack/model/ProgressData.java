package com.habitstack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Document(collection = "progress_data")
public class ProgressData {
    @Id
    private String id;
    
    @NotBlank(message = "Stack ID is required")
    @Field("stack_id")
    @JsonProperty("stack_id")
    private String stackId;
    
    @Min(value = 0, message = "Current streak cannot be negative")
    @Field("current_streak")
    @JsonProperty("current_streak")
    private Integer currentStreak = 0;
    
    @Min(value = 0, message = "Longest streak cannot be negative")
    @Field("longest_streak")
    @JsonProperty("longest_streak")
    private Integer longestStreak = 0;
    
    @Field("completion_rate")
    @JsonProperty("completion_rate")
    private Double completionRate = 0.0;
    
    @Field("last_week_progress")
    @JsonProperty("last_week_progress")
    private List<Boolean> lastWeekProgress;
    
    @Field("updated_at")
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    
    public ProgressData() {
        this.id = UUID.randomUUID().toString();
        this.updatedAt = LocalDateTime.now();
        this.lastWeekProgress = Arrays.asList(false, false, false, false, false, false, false);
    }
    
    public ProgressData(String stackId) {
        this();
        this.stackId = stackId;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getStackId() { return stackId; }
    public void setStackId(String stackId) { this.stackId = stackId; }
    
    public Integer getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(Integer currentStreak) { this.currentStreak = currentStreak; }
    
    public Integer getLongestStreak() { return longestStreak; }
    public void setLongestStreak(Integer longestStreak) { this.longestStreak = longestStreak; }
    
    public Double getCompletionRate() { return completionRate; }
    public void setCompletionRate(Double completionRate) { this.completionRate = completionRate; }
    
    public List<Boolean> getLastWeekProgress() { return lastWeekProgress; }
    public void setLastWeekProgress(List<Boolean> lastWeekProgress) { this.lastWeekProgress = lastWeekProgress; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}