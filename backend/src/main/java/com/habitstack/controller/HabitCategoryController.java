package com.habitstack.controller;

import com.habitstack.model.HabitCategory;
import com.habitstack.service.HabitCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@Validated
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class HabitCategoryController {
    
    private final HabitCategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<List<HabitCategory>> getAllCategories() {
        log.info("GET /categories - Fetching all categories");
        List<HabitCategory> categories = categoryService.getAllCategories();
        log.info("GET /categories - Successfully returned {} categories", categories.size());
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HabitCategory> getCategoryById(@PathVariable String id) {
        log.info("GET /categories/{} - Fetching category by ID", id);
        HabitCategory category = categoryService.getCategoryById(id);
        log.info("GET /categories/{} - Successfully returned category", id);
        return ResponseEntity.ok(category);
    }
    
    @PostMapping
    public ResponseEntity<HabitCategory> createCategory(@Valid @RequestBody HabitCategory category) {
        log.info("POST /categories - Creating new category: {}", category.getName());
        HabitCategory createdCategory = categoryService.createCategory(category);
        log.info("POST /categories - Successfully created category with ID: {}", createdCategory.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HabitCategory> updateCategory(@PathVariable String id, 
                                                      @Valid @RequestBody HabitCategory categoryDetails) {
        log.info("PUT /categories/{} - Updating category", id);
        HabitCategory updatedCategory = categoryService.updateCategory(id, categoryDetails);
        log.info("PUT /categories/{} - Successfully updated category", id);
        return ResponseEntity.ok(updatedCategory);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        log.info("DELETE /categories/{} - Deleting category", id);
        categoryService.deleteCategory(id);
        log.info("DELETE /categories/{} - Successfully deleted category", id);
        return ResponseEntity.ok().build();
    }
}