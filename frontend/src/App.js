import React, { useState, useEffect } from "react";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Toaster } from './components/ui/toaster';
import Dashboard from './components/Dashboard';
import { 
  habits as initialHabits,
  habitCategories,
  habitStacks as initialHabitStacks,
  progressData as initialProgressData
} from './data/mockData';

function App() {
  const [habits] = useState(initialHabits);
  const [categories] = useState(habitCategories);
  const [habitStacks, setHabitStacks] = useState(initialHabitStacks);
  const [progressData, setProgressData] = useState(initialProgressData);

  // Load data from localStorage on component mount
  useEffect(() => {
    const savedStacks = localStorage.getItem('habitStacks');
    const savedProgress = localStorage.getItem('progressData');
    
    if (savedStacks) {
      try {
        const parsedStacks = JSON.parse(savedStacks);
        setHabitStacks(parsedStacks);
      } catch (error) {
        console.error('Error parsing saved stacks:', error);
      }
    }
    
    if (savedProgress) {
      try {
        const parsedProgress = JSON.parse(savedProgress);
        setProgressData(parsedProgress);
      } catch (error) {
        console.error('Error parsing saved progress:', error);
      }
    }
  }, []);

  // Save data to localStorage whenever it changes
  useEffect(() => {
    localStorage.setItem('habitStacks', JSON.stringify(habitStacks));
  }, [habitStacks]);

  useEffect(() => {
    localStorage.setItem('progressData', JSON.stringify(progressData));
  }, [progressData]);

  const handleSaveStack = (newStack) => {
    setHabitStacks(prev => [...prev, newStack]);
    
    // Initialize progress data for new stack
    setProgressData(prev => ({
      ...prev,
      [newStack.id]: {
        currentStreak: 0,
        longestStreak: 0,
        completionRate: 0,
        lastWeekProgress: Array(7).fill(false)
      }
    }));
  };

  const handleDeleteStack = (stackId) => {
    setHabitStacks(prev => prev.filter(stack => stack.id !== stackId));
    setProgressData(prev => {
      const newProgress = { ...prev };
      delete newProgress[stackId];
      return newProgress;
    });
  };

  const handleToggleComplete = (stackId, habitId) => {
    setHabitStacks(prev => prev.map(stack => {
      if (stack.id === stackId) {
        const updatedHabits = stack.habits.map(habit => 
          habit.habitId === habitId 
            ? { ...habit, completed: !habit.completed }
            : habit
        );
        
        return {
          ...stack,
          habits: updatedHabits,
          lastCompleted: updatedHabits.every(h => h.completed) ? new Date() : stack.lastCompleted
        };
      }
      return stack;
    }));

    // Update progress data
    setProgressData(prev => {
      const stackData = prev[stackId] || {
        currentStreak: 0,
        longestStreak: 0,
        completionRate: 0,
        lastWeekProgress: Array(7).fill(false)
      };
      
      const stack = habitStacks.find(s => s.id === stackId);
      if (stack) {
        const completedCount = stack.habits.filter(h => h.completed).length;
        const totalCount = stack.habits.length;
        const newCompletionRate = totalCount > 0 ? completedCount / totalCount : 0;
        
        return {
          ...prev,
          [stackId]: {
            ...stackData,
            completionRate: newCompletionRate,
            currentStreak: newCompletionRate === 1 ? stackData.currentStreak + 1 : stackData.currentStreak,
            longestStreak: Math.max(stackData.longestStreak, newCompletionRate === 1 ? stackData.currentStreak + 1 : stackData.currentStreak)
          }
        };
      }
      
      return prev;
    });
  };

  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route 
            path="/" 
            element={
              <Dashboard
                habits={habits}
                categories={categories}
                habitStacks={habitStacks}
                progressData={progressData}
                onSaveStack={handleSaveStack}
                onDeleteStack={handleDeleteStack}
                onToggleComplete={handleToggleComplete}
              />
            } 
          />
        </Routes>
      </BrowserRouter>
      <Toaster />
    </div>
  );
}

export default App;