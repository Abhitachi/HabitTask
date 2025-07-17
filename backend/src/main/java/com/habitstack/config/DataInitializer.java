package com.habitstack.config;

import com.habitstack.model.Habit;
import com.habitstack.model.HabitCategory;
import com.habitstack.service.HabitCategoryService;
import com.habitstack.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private HabitCategoryService categoryService;
    
    @Autowired
    private HabitService habitService;
    
    @Override
    public void run(String... args) throws Exception {
        initializeDefaultCategories();
        initializeDefaultHabits();
    }
    
    private void initializeDefaultCategories() {
        List<HabitCategory> categories = categoryService.getAllCategories();
        
        if (categories.isEmpty()) {
            List<HabitCategory> defaultCategories = Arrays.asList(
                new HabitCategory("morning", "Morning Routine", "#FFB800", "🌅"),
                new HabitCategory("exercise", "Exercise", "#FF6B6B", "💪"),
                new HabitCategory("work", "Work", "#4ECDC4", "💼"),
                new HabitCategory("evening", "Evening Routine", "#A8E6CF", "🌙"),
                new HabitCategory("health", "Health", "#88D8B0", "🏥"),
                new HabitCategory("learning", "Learning", "#FFD93D", "📚")
            );
            
            for (HabitCategory category : defaultCategories) {
                category.setId(category.getName()); // Use name as ID for consistency
                categoryService.createCategory(category);
            }
            
            System.out.println("Default categories initialized");
        }
    }
    
    private void initializeDefaultHabits() {
        List<Habit> habits = habitService.getAllHabits();
        
        if (habits.isEmpty()) {
            List<Habit> defaultHabits = Arrays.asList(
                // Morning Routine
                new Habit("Brush Teeth", "morning", 3, "Clean teeth and gums"),
                new Habit("Drink Water", "morning", 1, "16oz of water"),
                new Habit("Make Bed", "morning", 2, "Tidy up bedroom"),
                new Habit("Make Coffee", "morning", 5, "Brew morning coffee"),
                new Habit("Check Weather", "morning", 1, "Plan outfit for day"),
                
                // Exercise
                new Habit("10 Push-ups", "exercise", 2, "Quick strength training"),
                new Habit("5-min Stretch", "exercise", 5, "Basic stretching routine"),
                new Habit("Go for Walk", "exercise", 15, "15-minute walk outside"),
                new Habit("Planks", "exercise", 3, "1-minute plank hold"),
                new Habit("Jumping Jacks", "exercise", 2, "20 jumping jacks"),
                
                // Work
                new Habit("Check Email", "work", 10, "Review and respond to emails"),
                new Habit("Review To-Do", "work", 3, "Plan daily tasks"),
                new Habit("Deep Work Block", "work", 90, "Focused work session"),
                new Habit("Team Standup", "work", 15, "Daily team meeting"),
                
                // Evening Routine
                new Habit("Prepare Clothes", "evening", 5, "Lay out tomorrow's outfit"),
                new Habit("Journal", "evening", 10, "Write daily reflections"),
                new Habit("Read Book", "evening", 20, "Read before bed"),
                new Habit("Phone Away", "evening", 1, "Put phone in another room"),
                
                // Health
                new Habit("Take Vitamins", "health", 1, "Daily supplement routine"),
                new Habit("Meditate", "health", 10, "10-minute mindfulness"),
                new Habit("Floss", "health", 2, "Daily flossing routine"),
                
                // Learning
                new Habit("Duolingo", "learning", 15, "Language learning practice"),
                new Habit("Watch Tutorial", "learning", 20, "Educational video"),
                new Habit("Practice Code", "learning", 30, "Coding practice")
            );
            
            for (Habit habit : defaultHabits) {
                habitService.createHabit(habit);
            }
            
            System.out.println("Default habits initialized");
        }
    }
}