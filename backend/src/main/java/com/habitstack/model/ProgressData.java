package com.habitstack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Builder.Default
    private Integer currentStreak = 0;
    
    @Min(value = 0, message = "Longest streak cannot be negative")
    @Field("longest_streak")
    @JsonProperty("longest_streak")
    @Builder.Default
    private Integer longestStreak = 0;
    
    @Field("completion_rate")
    @JsonProperty("completion_rate")
    @Builder.Default
    private Double completionRate = 0.0;
    
    @Field("last_week_progress")
    @JsonProperty("last_week_progress")
    @Builder.Default
    private List<Boolean> lastWeekProgress = Arrays.asList(false, false, false, false, false, false, false);
    
    @Field("updated_at")
    @JsonProperty("updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    public ProgressData(String stackId) {
        this.id = UUID.randomUUID().toString();
        this.stackId = stackId;
        this.currentStreak = 0;
        this.longestStreak = 0;
        this.completionRate = 0.0;
        this.lastWeekProgress = Arrays.asList(false, false, false, false, false, false, false);
        this.updatedAt = LocalDateTime.now();
    }
}