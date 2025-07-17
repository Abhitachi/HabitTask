package com.habitstack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.UUID;

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
    private LocalDateTime createdAt;
    
    public Habit() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }
    
    public Habit(String name, String category, Integer time, String description) {
        this();
        this.name = name;
        this.category = category;
        this.time = time;
        this.description = description;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Integer getTime() { return time; }
    public void setTime(Integer time) { this.time = time; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}