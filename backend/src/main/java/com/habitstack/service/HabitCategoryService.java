package com.habitstack.service;

import com.habitstack.exception.DatabaseException;
import com.habitstack.exception.ResourceAlreadyExistsException;
import com.habitstack.exception.ResourceNotFoundException;
import com.habitstack.model.HabitCategory;
import com.habitstack.repository.HabitCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitCategoryService {
    
    private final HabitCategoryRepository categoryRepository;
    
    public List<HabitCategory> getAllCategories() {
        log.info("Fetching all habit categories");
        try {
            List<HabitCategory> categories = categoryRepository.findAll();
            log.info("Successfully retrieved {} categories", categories.size());
            return categories;
        } catch (DataAccessException e) {
            log.error("Database error while fetching categories: {}", e.getMessage(), e);
            throw new DatabaseException("Failed to fetch categories", e);
        }
    }
    
    public HabitCategory getCategoryById(String id) {
        log.info("Fetching category with ID: {}", id);
        try {
            return categoryRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Category not found with ID: {}", id);
                        return new ResourceNotFoundException("Category", id);
                    });
        } catch (DataAccessException e) {
            log.error("Database error while fetching category with ID {}: {}", id, e.getMessage(), e);
            throw new DatabaseException("Failed to fetch category", e);
        }
    }
    
    public Optional<HabitCategory> getCategoryByName(String name) {
        log.info("Fetching category with name: {}", name);
        try {
            Optional<HabitCategory> category = categoryRepository.findByName(name);
            if (category.isPresent()) {
                log.info("Found category with name: {}", name);
            } else {
                log.info("No category found with name: {}", name);
            }
            return category;
        } catch (DataAccessException e) {
            log.error("Database error while fetching category with name {}: {}", name, e.getMessage(), e);
            throw new DatabaseException("Failed to fetch category by name", e);
        }
    }
    
    public HabitCategory createCategory(HabitCategory category) {
        log.info("Creating new category: {}", category.getName());
        
        try {
            if (categoryRepository.existsByName(category.getName())) {
                log.warn("Category already exists with name: {}", category.getName());
                throw new ResourceAlreadyExistsException("Category", category.getName());
            }
            
            HabitCategory savedCategory = categoryRepository.save(category);
            log.info("Successfully created category with ID: {}", savedCategory.getId());
            return savedCategory;
        } catch (DataAccessException e) {
            log.error("Database error while creating category {}: {}", category.getName(), e.getMessage(), e);
            throw new DatabaseException("Failed to create category", e);
        }
    }
    
    public HabitCategory updateCategory(String id, HabitCategory categoryDetails) {
        log.info("Updating category with ID: {}", id);
        
        try {
            HabitCategory existingCategory = categoryRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Category not found for update with ID: {}", id);
                        return new ResourceNotFoundException("Category", id);
                    });
            
            // Check if new name conflicts with existing category
            if (!existingCategory.getName().equals(categoryDetails.getName()) && 
                categoryRepository.existsByName(categoryDetails.getName())) {
                log.warn("Category name already exists: {}", categoryDetails.getName());
                throw new ResourceAlreadyExistsException("Category", categoryDetails.getName());
            }
            
            existingCategory.setName(categoryDetails.getName());
            existingCategory.setColor(categoryDetails.getColor());
            existingCategory.setIcon(categoryDetails.getIcon());
            
            HabitCategory updatedCategory = categoryRepository.save(existingCategory);
            log.info("Successfully updated category with ID: {}", id);
            return updatedCategory;
        } catch (DataAccessException e) {
            log.error("Database error while updating category with ID {}: {}", id, e.getMessage(), e);
            throw new DatabaseException("Failed to update category", e);
        }
    }
    
    public void deleteCategory(String id) {
        log.info("Deleting category with ID: {}", id);
        
        try {
            if (!categoryRepository.existsById(id)) {
                log.warn("Category not found for deletion with ID: {}", id);
                throw new ResourceNotFoundException("Category", id);
            }
            
            categoryRepository.deleteById(id);
            log.info("Successfully deleted category with ID: {}", id);
        } catch (DataAccessException e) {
            log.error("Database error while deleting category with ID {}: {}", id, e.getMessage(), e);
            throw new DatabaseException("Failed to delete category", e);
        }
    }
    
    public boolean existsByName(String name) {
        log.debug("Checking if category exists with name: {}", name);
        try {
            boolean exists = categoryRepository.existsByName(name);
            log.debug("Category exists check for name {}: {}", name, exists);
            return exists;
        } catch (DataAccessException e) {
            log.error("Database error while checking category existence with name {}: {}", name, e.getMessage(), e);
            throw new DatabaseException("Failed to check category existence", e);
        }
    }
}