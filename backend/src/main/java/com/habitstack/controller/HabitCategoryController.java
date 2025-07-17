package com.habitstack.controller;

import com.habitstack.model.HabitCategory;
import com.habitstack.service.HabitCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@Validated
@CrossOrigin(origins = "*")
public class HabitCategoryController {
    
    @Autowired
    private HabitCategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<List<HabitCategory>> getAllCategories() {
        List<HabitCategory> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HabitCategory> getCategoryById(@PathVariable String id) {
        Optional<HabitCategory> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<HabitCategory> createCategory(@Valid @RequestBody HabitCategory category) {
        try {
            if (categoryService.existsByName(category.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            HabitCategory createdCategory = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HabitCategory> updateCategory(@PathVariable String id, 
                                                      @Valid @RequestBody HabitCategory categoryDetails) {
        try {
            HabitCategory updatedCategory = categoryService.updateCategory(id, categoryDetails);
            return updatedCategory != null ? ResponseEntity.ok(updatedCategory) 
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        try {
            boolean deleted = categoryService.deleteCategory(id);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}