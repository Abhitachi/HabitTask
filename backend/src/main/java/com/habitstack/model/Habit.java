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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "habits")
public class Habit {
    @Id
    private String id;
    
    @NotBlank(message = "Habit name is required")
    private String name;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotNull(message = "Time is required")
    @Min(value = 1, message = "Time must be at least 1 minute")
    private Integer time;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @Field("created_at")
    @JsonProperty("created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public Habit(String name, String category, Integer time, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.time = time;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }
}