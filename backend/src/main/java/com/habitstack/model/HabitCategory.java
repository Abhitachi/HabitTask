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
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "habit_categories")
public class HabitCategory {
    @Id
    private String id;
    
    @NotBlank(message = "Category name is required")
    private String name;
    
    @NotBlank(message = "Stack name is required")
    private String stack;

    @NotBlank(message = "Category color is required")
    private String color;
    
    @NotBlank(message = "Category icon is required")
    private String icon;
    
    @Field("created_at")
    @JsonProperty("created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public HabitCategory(String stack, String name, String color, String icon) {
        this.id = UUID.randomUUID().toString();
        this.stack = stack;
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.createdAt = LocalDateTime.now();
    }
}