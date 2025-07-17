package com.habitstack.config;

import com.habitstack.model.Habit;
import com.habitstack.model.HabitCategory;
import com.habitstack.model.HabitStack;
import com.habitstack.model.StackHabit;
import com.habitstack.model.ProgressData;
import com.habitstack.service.HabitCategoryService;
import com.habitstack.service.HabitService;
import com.habitstack.service.HabitStackService;
import com.habitstack.service.ProgressDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final HabitCategoryService categoryService;
    private final HabitService habitService;
    private final HabitStackService stackService;
    private final ProgressDataService progressService;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Starting data initialization...");
        
        try {
            initializeHabitCategories();
            initializeHabits();
            initializeHabitStacks();
            initializeProgressData();
            
            log.info("Data initialization completed successfully!");
        } catch (Exception e) {
            log.error("Error during data initialization: {}", e.getMessage(), e);
        }
    }
    
    private void initializeHabitCategories() {
        log.info("Initializing habit categories...");
        
        List<HabitCategory> categories = categoryService.getAllCategories();
        
        if (categories.isEmpty()) {
            List<HabitCategory> defaultCategories = Arrays.asList(
                HabitCategory.builder()
                    .id("morning")
                    .name("Morning Routine")
                    .color("#FFB800")
                    .icon("🌅")
                    .createdAt(LocalDateTime.now())
                    .build(),
                HabitCategory.builder()
                    .id("exercise")
                    .name("Exercise")
                    .color("#FF6B6B")
                    .icon("💪")
                    .createdAt(LocalDateTime.now())
                    .build(),
                HabitCategory.builder()
                    .id("work")
                    .name("Work")
                    .color("#4ECDC4")
                    .icon("💼")
                    .createdAt(LocalDateTime.now())
                    .build(),
                HabitCategory.builder()
                    .id("evening")
                    .name("Evening Routine")
                    .color("#A8E6CF")
                    .icon("🌙")
                    .createdAt(LocalDateTime.now())
                    .build(),
                HabitCategory.builder()
                    .id("health")
                    .name("Health")
                    .color("#88D8B0")
                    .icon("🏥")
                    .createdAt(LocalDateTime.now())
                    .build(),
                HabitCategory.builder()
                    .id("learning")
                    .name("Learning")
                    .color("#FFD93D")
                    .icon("📚")
                    .createdAt(LocalDateTime.now())
                    .build()
            );
            
            for (HabitCategory category : defaultCategories) {
                categoryService.createCategory(category);
            }
            
            log.info("Successfully initialized {} habit categories", defaultCategories.size());
        } else {
            log.info("Habit categories already exist, skipping initialization");
        }
    }
    
    private void initializeHabits() {
        log.info("Initializing habits...");
        
        List<Habit> habits = habitService.getAllHabits();
        
        if (habits.isEmpty()) {
            List<Habit> defaultHabits = Arrays.asList(
                // Morning Routine
                Habit.builder().id("1").name("Brush Teeth").category("morning").time(3).description("Clean teeth and gums").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("2").name("Drink Water").category("morning").time(1).description("16oz of water").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("3").name("Make Bed").category("morning").time(2).description("Tidy up bedroom").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("4").name("Make Coffee").category("morning").time(5).description("Brew morning coffee").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("5").name("Check Weather").category("morning").time(1).description("Plan outfit for day").createdAt(LocalDateTime.now()).build(),
                
                // Exercise
                Habit.builder().id("6").name("10 Push-ups").category("exercise").time(2).description("Quick strength training").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("7").name("5-min Stretch").category("exercise").time(5).description("Basic stretching routine").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("8").name("Go for Walk").category("exercise").time(15).description("15-minute walk outside").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("9").name("Planks").category("exercise").time(3).description("1-minute plank hold").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("10").name("Jumping Jacks").category("exercise").time(2).description("20 jumping jacks").createdAt(LocalDateTime.now()).build(),
                
                // Work
                Habit.builder().id("11").name("Check Email").category("work").time(10).description("Review and respond to emails").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("12").name("Review To-Do").category("work").time(3).description("Plan daily tasks").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("13").name("Deep Work Block").category("work").time(90).description("Focused work session").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("14").name("Team Standup").category("work").time(15).description("Daily team meeting").createdAt(LocalDateTime.now()).build(),
                
                // Evening Routine
                Habit.builder().id("15").name("Prepare Clothes").category("evening").time(5).description("Lay out tomorrow's outfit").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("16").name("Journal").category("evening").time(10).description("Write daily reflections").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("17").name("Read Book").category("evening").time(20).description("Read before bed").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("18").name("Phone Away").category("evening").time(1).description("Put phone in another room").createdAt(LocalDateTime.now()).build(),
                
                // Health
                Habit.builder().id("19").name("Take Vitamins").category("health").time(1).description("Daily supplement routine").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("20").name("Meditate").category("health").time(10).description("10-minute mindfulness").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("21").name("Floss").category("health").time(2).description("Daily flossing routine").createdAt(LocalDateTime.now()).build(),
                
                // Learning
                Habit.builder().id("22").name("Duolingo").category("learning").time(15).description("Language learning practice").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("23").name("Watch Tutorial").category("learning").time(20).description("Educational video").createdAt(LocalDateTime.now()).build(),
                Habit.builder().id("24").name("Practice Code").category("learning").time(30).description("Coding practice").createdAt(LocalDateTime.now()).build()
            );
            
            for (Habit habit : defaultHabits) {
                habitService.createHabit(habit);
            }
            
            log.info("Successfully initialized {} habits", defaultHabits.size());
        } else {
            log.info("Habits already exist, skipping initialization");
        }
    }
    
    private void initializeHabitStacks() {
        log.info("Initializing habit stacks...");
        
        List<HabitStack> stacks = stackService.getAllStacks();
        
        if (stacks.isEmpty()) {
            List<HabitStack> defaultStacks = Arrays.asList(
                HabitStack.builder()
                    .id("stack1")
                    .name("Morning Power Stack")
                    .habits(Arrays.asList(
                        StackHabit.builder().habitId("1").completed(true).build(),
                        StackHabit.builder().habitId("6").completed(true).build(),
                        StackHabit.builder().habitId("4").completed(false).build(),
                        StackHabit.builder().habitId("20").completed(false).build()
                    ))
                    .createdAt(LocalDateTime.of(2024, 1, 15, 10, 0))
                    .lastCompleted(LocalDateTime.of(2024, 1, 20, 8, 30))
                    .build(),
                
                HabitStack.builder()
                    .id("stack2")
                    .name("Work Prep Stack")
                    .habits(Arrays.asList(
                        StackHabit.builder().habitId("2").completed(true).build(),
                        StackHabit.builder().habitId("12").completed(true).build(),
                        StackHabit.builder().habitId("11").completed(false).build()
                    ))
                    .createdAt(LocalDateTime.of(2024, 1, 16, 9, 0))
                    .lastCompleted(LocalDateTime.of(2024, 1, 19, 9, 15))
                    .build(),
                
                HabitStack.builder()
                    .id("stack3")
                    .name("Evening Wind Down")
                    .habits(Arrays.asList(
                        StackHabit.builder().habitId("15").completed(false).build(),
                        StackHabit.builder().habitId("16").completed(false).build(),
                        StackHabit.builder().habitId("17").completed(false).build(),
                        StackHabit.builder().habitId("18").completed(false).build()
                    ))
                    .createdAt(LocalDateTime.of(2024, 1, 17, 20, 0))
                    .lastCompleted(null)
                    .build()
            );
            
            for (HabitStack stack : defaultStacks) {
                stackService.createStack(stack);
            }
            
            log.info("Successfully initialized {} habit stacks", defaultStacks.size());
        } else {
            log.info("Habit stacks already exist, skipping initialization");
        }
    }
    
    private void initializeProgressData() {
        log.info("Initializing progress data...");
        
        List<ProgressData> progressList = progressService.getAllProgress();
        
        if (progressList.isEmpty()) {
            List<ProgressData> defaultProgressData = Arrays.asList(
                ProgressData.builder()
                    .stackId("stack1")
                    .currentStreak(5)
                    .longestStreak(12)
                    .completionRate(0.85)
                    .lastWeekProgress(Arrays.asList(true, true, false, true, true, true, false))
                    .updatedAt(LocalDateTime.now())
                    .build(),
                
                ProgressData.builder()
                    .stackId("stack2")
                    .currentStreak(3)
                    .longestStreak(8)
                    .completionRate(0.92)
                    .lastWeekProgress(Arrays.asList(true, true, true, false, true, true, true))
                    .updatedAt(LocalDateTime.now())
                    .build(),
                
                ProgressData.builder()
                    .stackId("stack3")
                    .currentStreak(0)
                    .longestStreak(4)
                    .completionRate(0.45)
                    .lastWeekProgress(Arrays.asList(false, false, true, false, false, true, false))
                    .updatedAt(LocalDateTime.now())
                    .build()
            );
            
            for (ProgressData progressData : defaultProgressData) {
                progressService.createOrUpdateProgress(progressData);
            }
            
            log.info("Successfully initialized {} progress data records", defaultProgressData.size());
        } else {
            log.info("Progress data already exists, skipping initialization");
        }
    }
}