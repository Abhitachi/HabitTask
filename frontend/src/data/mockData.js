// Mock data for Habit Stack Builder

export const habitCategories = [
  { id: 'morning', name: 'Morning Routine', color: '#FFB800', icon: '🌅' },
  { id: 'exercise', name: 'Exercise', color: '#FF6B6B', icon: '💪' },
  { id: 'work', name: 'Work', color: '#4ECDC4', icon: '💼' },
  { id: 'evening', name: 'Evening Routine', color: '#A8E6CF', icon: '🌙' },
  { id: 'health', name: 'Health', color: '#88D8B0', icon: '🏥' },
  { id: 'learning', name: 'Learning', color: '#FFD93D', icon: '📚' }
];

export const habits = [
  // Morning Routine
  { id: '1', name: 'Brush Teeth', category: 'morning', time: 3, description: 'Clean teeth and gums' },
  { id: '2', name: 'Drink Water', category: 'morning', time: 1, description: '16oz of water' },
  { id: '3', name: 'Make Bed', category: 'morning', time: 2, description: 'Tidy up bedroom' },
  { id: '4', name: 'Make Coffee', category: 'morning', time: 5, description: 'Brew morning coffee' },
  { id: '5', name: 'Check Weather', category: 'morning', time: 1, description: 'Plan outfit for day' },
  
  // Exercise
  { id: '6', name: '10 Push-ups', category: 'exercise', time: 2, description: 'Quick strength training' },
  { id: '7', name: '5-min Stretch', category: 'exercise', time: 5, description: 'Basic stretching routine' },
  { id: '8', name: 'Go for Walk', category: 'exercise', time: 15, description: '15-minute walk outside' },
  { id: '9', name: 'Planks', category: 'exercise', time: 3, description: '1-minute plank hold' },
  { id: '10', name: 'Jumping Jacks', category: 'exercise', time: 2, description: '20 jumping jacks' },
  
  // Work
  { id: '11', name: 'Check Email', category: 'work', time: 10, description: 'Review and respond to emails' },
  { id: '12', name: 'Review To-Do', category: 'work', time: 3, description: 'Plan daily tasks' },
  { id: '13', name: 'Deep Work Block', category: 'work', time: 90, description: 'Focused work session' },
  { id: '14', name: 'Team Standup', category: 'work', time: 15, description: 'Daily team meeting' },
  
  // Evening Routine
  { id: '15', name: 'Prepare Clothes', category: 'evening', time: 5, description: 'Lay out tomorrow\'s outfit' },
  { id: '16', name: 'Journal', category: 'evening', time: 10, description: 'Write daily reflections' },
  { id: '17', name: 'Read Book', category: 'evening', time: 20, description: 'Read before bed' },
  { id: '18', name: 'Phone Away', category: 'evening', time: 1, description: 'Put phone in another room' },
  
  // Health
  { id: '19', name: 'Take Vitamins', category: 'health', time: 1, description: 'Daily supplement routine' },
  { id: '20', name: 'Meditate', category: 'health', time: 10, description: '10-minute mindfulness' },
  { id: '21', name: 'Floss', category: 'health', time: 2, description: 'Daily flossing routine' },
  
  // Learning
  { id: '22', name: 'Duolingo', category: 'learning', time: 15, description: 'Language learning practice' },
  { id: '23', name: 'Watch Tutorial', category: 'learning', time: 20, description: 'Educational video' },
  { id: '24', name: 'Practice Code', category: 'learning', time: 30, description: 'Coding practice' }
];

export const habitStacks = [
  {
    id: 'stack1',
    name: 'Morning Power Stack',
    habits: [
      { habitId: '1', completed: true },
      { habitId: '6', completed: true },
      { habitId: '4', completed: false },
      { habitId: '20', completed: false }
    ],
    createdAt: new Date('2024-01-15'),
    lastCompleted: new Date('2024-01-20')
  },
  {
    id: 'stack2',
    name: 'Work Prep Stack',
    habits: [
      { habitId: '2', completed: true },
      { habitId: '12', completed: true },
      { habitId: '11', completed: false }
    ],
    createdAt: new Date('2024-01-16'),
    lastCompleted: new Date('2024-01-19')
  },
  {
    id: 'stack3',
    name: 'Evening Wind Down',
    habits: [
      { habitId: '15', completed: false },
      { habitId: '16', completed: false },
      { habitId: '17', completed: false },
      { habitId: '18', completed: false }
    ],
    createdAt: new Date('2024-01-17'),
    lastCompleted: null
  }
];

// Progress tracking data
export const progressData = {
  stack1: {
    currentStreak: 5,
    longestStreak: 12,
    completionRate: 0.85,
    lastWeekProgress: [true, true, false, true, true, true, false]
  },
  stack2: {
    currentStreak: 3,
    longestStreak: 8,
    completionRate: 0.92,
    lastWeekProgress: [true, true, true, false, true, true, true]
  },
  stack3: {
    currentStreak: 0,
    longestStreak: 4,
    completionRate: 0.45,
    lastWeekProgress: [false, false, true, false, false, true, false]
  }
};