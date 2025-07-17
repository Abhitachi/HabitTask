package com.habitstack.config;

import com.habitstack.model.HabitCategory;
import com.habitstack.repository.HabitCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final HabitCategoryRepository habitCategoryRepository;

    public DataInitializer(HabitCategoryRepository habitCategoryRepository) {
        this.habitCategoryRepository = habitCategoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing seed data for HabitCategory");

        // Check if data already exists to avoid duplicates
        if (habitCategoryRepository.count() == 0) {
            habitCategoryRepository.save(new HabitCategory("Fitness", "#0000FF", "dumbbell", "user123"));
            habitCategoryRepository.save(new HabitCategory("Mental Health", "#00FF00", "brain", "user123"));
            habitCategoryRepository.save(new HabitCategory("Productivity", "#FF0000", "calendar", "user123"));
            habitCategoryRepository.save(new HabitCategory("Nutrition", "#FFFF00", "apple", "user123"));
            habitCategoryRepository.save(new HabitCategory("Sleep", "#800080", "bed", "user123"));
            habitCategoryRepository.save(new HabitCategory("Social", "#FFA500", "users", "user123"));
            log.info("Seed data for HabitCategory loaded successfully");
        } else {
            log.info("Seed data already exists, skipping initialization");
        }
    }
}