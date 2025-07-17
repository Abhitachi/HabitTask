// MongoDB seeding script for Habit Stack Builder
// Run this script with: mongosh habitstack seed_data.js

// Clear existing data
db.habit_categories.deleteMany({});
db.habits.deleteMany({});
db.habit_stacks.deleteMany({});
db.progress_data.deleteMany({});

print("Cleared existing data");

// Insert habit categories
db.habit_categories.insertMany([
  {
    "_id": "morning",
    "id": "morning",
    "name": "Morning Routine",
    "color": "#FFB800",
    "icon": "🌅",
    "created_at": new Date()
  },
  {
    "_id": "exercise",
    "id": "exercise",
    "name": "Exercise",
    "color": "#FF6B6B",
    "icon": "💪",
    "created_at": new Date()
  },
  {
    "_id": "work",
    "id": "work",
    "name": "Work",
    "color": "#4ECDC4",
    "icon": "💼",
    "created_at": new Date()
  },
  {
    "_id": "evening",
    "id": "evening",
    "name": "Evening Routine",
    "color": "#A8E6CF",
    "icon": "🌙",
    "created_at": new Date()
  },
  {
    "_id": "health",
    "id": "health",
    "name": "Health",
    "color": "#88D8B0",
    "icon": "🏥",
    "created_at": new Date()
  },
  {
    "_id": "learning",
    "id": "learning",
    "name": "Learning",
    "color": "#FFD93D",
    "icon": "📚",
    "created_at": new Date()
  }
]);

print("Inserted habit categories");

// Insert habits
db.habits.insertMany([
  // Morning Routine
  { "_id": "1", "id": "1", "name": "Brush Teeth", "category": "morning", "time": 3, "description": "Clean teeth and gums", "created_at": new Date() },
  { "_id": "2", "id": "2", "name": "Drink Water", "category": "morning", "time": 1, "description": "16oz of water", "created_at": new Date() },
  { "_id": "3", "id": "3", "name": "Make Bed", "category": "morning", "time": 2, "description": "Tidy up bedroom", "created_at": new Date() },
  { "_id": "4", "id": "4", "name": "Make Coffee", "category": "morning", "time": 5, "description": "Brew morning coffee", "created_at": new Date() },
  { "_id": "5", "id": "5", "name": "Check Weather", "category": "morning", "time": 1, "description": "Plan outfit for day", "created_at": new Date() },
  
  // Exercise
  { "_id": "6", "id": "6", "name": "10 Push-ups", "category": "exercise", "time": 2, "description": "Quick strength training", "created_at": new Date() },
  { "_id": "7", "id": "7", "name": "5-min Stretch", "category": "exercise", "time": 5, "description": "Basic stretching routine", "created_at": new Date() },
  { "_id": "8", "id": "8", "name": "Go for Walk", "category": "exercise", "time": 15, "description": "15-minute walk outside", "created_at": new Date() },
  { "_id": "9", "id": "9", "name": "Planks", "category": "exercise", "time": 3, "description": "1-minute plank hold", "created_at": new Date() },
  { "_id": "10", "id": "10", "name": "Jumping Jacks", "category": "exercise", "time": 2, "description": "20 jumping jacks", "created_at": new Date() },
  
  // Work
  { "_id": "11", "id": "11", "name": "Check Email", "category": "work", "time": 10, "description": "Review and respond to emails", "created_at": new Date() },
  { "_id": "12", "id": "12", "name": "Review To-Do", "category": "work", "time": 3, "description": "Plan daily tasks", "created_at": new Date() },
  { "_id": "13", "id": "13", "name": "Deep Work Block", "category": "work", "time": 90, "description": "Focused work session", "created_at": new Date() },
  { "_id": "14", "id": "14", "name": "Team Standup", "category": "work", "time": 15, "description": "Daily team meeting", "created_at": new Date() },
  
  // Evening Routine
  { "_id": "15", "id": "15", "name": "Prepare Clothes", "category": "evening", "time": 5, "description": "Lay out tomorrow's outfit", "created_at": new Date() },
  { "_id": "16", "id": "16", "name": "Journal", "category": "evening", "time": 10, "description": "Write daily reflections", "created_at": new Date() },
  { "_id": "17", "id": "17", "name": "Read Book", "category": "evening", "time": 20, "description": "Read before bed", "created_at": new Date() },
  { "_id": "18", "id": "18", "name": "Phone Away", "category": "evening", "time": 1, "description": "Put phone in another room", "created_at": new Date() },
  
  // Health
  { "_id": "19", "id": "19", "name": "Take Vitamins", "category": "health", "time": 1, "description": "Daily supplement routine", "created_at": new Date() },
  { "_id": "20", "id": "20", "name": "Meditate", "category": "health", "time": 10, "description": "10-minute mindfulness", "created_at": new Date() },
  { "_id": "21", "id": "21", "name": "Floss", "category": "health", "time": 2, "description": "Daily flossing routine", "created_at": new Date() },
  
  // Learning
  { "_id": "22", "id": "22", "name": "Duolingo", "category": "learning", "time": 15, "description": "Language learning practice", "created_at": new Date() },
  { "_id": "23", "id": "23", "name": "Watch Tutorial", "category": "learning", "time": 20, "description": "Educational video", "created_at": new Date() },
  { "_id": "24", "id": "24", "name": "Practice Code", "category": "learning", "time": 30, "description": "Coding practice", "created_at": new Date() }
]);

print("Inserted habits");

// Insert habit stacks
db.habit_stacks.insertMany([
  {
    "_id": "stack1",
    "id": "stack1",
    "name": "Morning Power Stack",
    "habits": [
      { "habitId": "1", "completed": true },
      { "habitId": "6", "completed": true },
      { "habitId": "4", "completed": false },
      { "habitId": "20", "completed": false }
    ],
    "created_at": new Date("2024-01-15T10:00:00Z"),
    "lastCompleted": new Date("2024-01-20T08:30:00Z")
  },
  {
    "_id": "stack2",
    "id": "stack2",
    "name": "Work Prep Stack",
    "habits": [
      { "habitId": "2", "completed": true },
      { "habitId": "12", "completed": true },
      { "habitId": "11", "completed": false }
    ],
    "created_at": new Date("2024-01-16T09:00:00Z"),
    "lastCompleted": new Date("2024-01-19T09:15:00Z")
  },
  {
    "_id": "stack3",
    "id": "stack3",
    "name": "Evening Wind Down",
    "habits": [
      { "habitId": "15", "completed": false },
      { "habitId": "16", "completed": false },
      { "habitId": "17", "completed": false },
      { "habitId": "18", "completed": false }
    ],
    "created_at": new Date("2024-01-17T20:00:00Z"),
    "lastCompleted": null
  }
]);

print("Inserted habit stacks");

// Insert progress data
db.progress_data.insertMany([
  {
    "_id": "progress1",
    "id": "progress1",
    "stack_id": "stack1",
    "current_streak": 5,
    "longest_streak": 12,
    "completion_rate": 0.85,
    "last_week_progress": [true, true, false, true, true, true, false],
    "updated_at": new Date()
  },
  {
    "_id": "progress2",
    "id": "progress2",
    "stack_id": "stack2",
    "current_streak": 3,
    "longest_streak": 8,
    "completion_rate": 0.92,
    "last_week_progress": [true, true, true, false, true, true, true],
    "updated_at": new Date()
  },
  {
    "_id": "progress3",
    "id": "progress3",
    "stack_id": "stack3",
    "current_streak": 0,
    "longest_streak": 4,
    "completion_rate": 0.45,
    "last_week_progress": [false, false, true, false, false, true, false],
    "updated_at": new Date()
  }
]);

print("Inserted progress data");

// Verify data insertion
print("\nData verification:");
print("Categories count:", db.habit_categories.countDocuments());
print("Habits count:", db.habits.countDocuments());
print("Habit stacks count:", db.habit_stacks.countDocuments());
print("Progress data count:", db.progress_data.countDocuments());

print("\nSample data:");
print("Sample category:", db.habit_categories.findOne());
print("Sample habit:", db.habits.findOne());
print("Sample stack:", db.habit_stacks.findOne());
print("Sample progress:", db.progress_data.findOne());

print("\nData seeding completed successfully!");