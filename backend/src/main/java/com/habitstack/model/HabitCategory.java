package com.habitstack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "habit_categories")
public class HabitCategory {
    @Id
    private String id;
    
    @NotBlank(message = "Category name is required")
    private String name;
    
    @NotBlank(message = "Category color is required")
    private String color;
    
    @NotBlank(message = "Category icon is required")
    private String icon;
    
    @Field("created_at")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    public HabitCategory() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }
    
    public HabitCategory(String name, String color, String icon) {
        this();
        this.name = name;
        this.color = color;
        this.icon = icon;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}