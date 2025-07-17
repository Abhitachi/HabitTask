package com.habitstack.service;

import com.habitstack.model.HabitCategory;
import com.habitstack.repository.HabitCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitCategoryService {
    
    @Autowired
    private HabitCategoryRepository categoryRepository;
    
    public List<HabitCategory> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public Optional<HabitCategory> getCategoryById(String id) {
        return categoryRepository.findById(id);
    }
    
    public Optional<HabitCategory> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
    
    public HabitCategory createCategory(HabitCategory category) {
        return categoryRepository.save(category);
    }
    
    public HabitCategory updateCategory(String id, HabitCategory categoryDetails) {
        Optional<HabitCategory> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            HabitCategory category = optionalCategory.get();
            category.setName(categoryDetails.getName());
            category.setColor(categoryDetails.getColor());
            category.setIcon(categoryDetails.getIcon());
            return categoryRepository.save(category);
        }
        return null;
    }
    
    public boolean deleteCategory(String id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}