import React, { useState, useEffect } from "react";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Toaster } from './components/ui/toaster';
import Dashboard from './components/Dashboard';
import { api } from './services/api';
import { useToast } from './hooks/use-toast';

function App() {
  const [habits, setHabits] = useState([]);
  const [categories, setCategories] = useState([]);
  const [habitStacks, setHabitStacks] = useState([]);
  const [progressData, setProgressData] = useState({});
  const [isLoading, setIsLoading] = useState(true);
  const { toast } = useToast();

  // Load data from API on component mount
  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setIsLoading(true);
      
      // Load all data in parallel
      const [categoriesData, habitsData, stacksData, progressArray] = await Promise.all([
        api.categories.getAll(),
        api.habits.getAll(),
        api.stacks.getAll(),
        api.progress.getAll()
      ]);

      setCategories(categoriesData);
      setHabits(habitsData);
      setHabitStacks(stacksData);
      
      // Convert progress array to object keyed by stack_id
      const progressObj = {};
      progressArray.forEach(progress => {
        progressObj[progress.stack_id] = progress;
      });
      setProgressData(progressObj);
      
    } catch (error) {
      console.error('Error loading data:', error);
      toast({
        title: "Error",
        description: "Failed to load data. Please try again.",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleSaveStack = async (newStack) => {
    try {
      const createdStack = await api.stacks.create(newStack);
      console.log(createdStack, 'Created stack')
      setHabitStacks(prev => [...prev, createdStack]);
      
      // The backend automatically creates progress data, so we need to fetch it
      const progressForStack = await api.progress.get(createdStack.id);
      console.log(progressForStack, "progress for stack")
      setProgressData(prev => ({
        ...prev,
        [createdStack.id]: progressForStack
      }));
      
      toast({
        title: "Stack Created!",
        description: `"${createdStack.name}" has been saved to your habit stacks.`,
      });
    } catch (error) {
      console.error('Error saving stack:', error);
      toast({
        title: "Error",
        description: "Failed to save stack. Please try again.",
        variant: "destructive",
      });
    }
  };

  const handleDeleteStack = async (stackId) => {
    try {
      await api.stacks.delete(stackId);
      setHabitStacks(prev => prev.filter(stack => stack.id !== stackId));
      
      // Remove progress data for deleted stack
      setProgressData(prev => {
        const newProgress = { ...prev };
        delete newProgress[stackId];
        return newProgress;
      });
      
      toast({
        title: "Stack Deleted",
        description: "Habit stack has been removed.",
      });
    } catch (error) {
      console.error('Error deleting stack:', error);
      toast({
        title: "Error",
        description: "Failed to delete stack. Please try again.",
        variant: "destructive",
      });
    }
  };

  const handleToggleComplete = async (stackId, habitId) => {
    try {
      // Optimistically update UI
      setHabitStacks(prev => prev.map(stack => {
        if (stack.id === stackId) {
          const updatedHabits = stack.habits.map(habit => 
            habit.habitId === habitId || habit.habit_id === habitId
              ? { ...habit, completed: !habit.completed }
              : habit
          );
          
          const allCompleted = updatedHabits.every(h => h.completed);
          
          return {
            ...stack,
            habits: updatedHabits,
            lastCompleted: allCompleted ? new Date().toISOString() : stack.lastCompleted
          };
        }
        return stack;
      }));

      // Make API call to toggle completion
      await api.toggleHabit(stackId, habitId);
      
      // Refresh progress data
      const updatedProgress = await api.progress.get(stackId);
      setProgressData(prev => ({
        ...prev,
        [stackId]: updatedProgress
      }));
      
    } catch (error) {
      console.error('Error toggling habit completion:', error);
      toast({
        title: "Error",
        description: "Failed to update habit completion. Please try again.",
        variant: "destructive",
      });
      // Revert optimistic update on error
      await loadData();
    }
  };

  const handleCreateHabit = async (newHabit) => {
    try {
      const createdHabit = await api.habits.create(newHabit);
      setHabits(prev => [...prev, createdHabit]);
      
      toast({
        title: "Habit Created!",
        description: `"${createdHabit.name}" has been added to your habit library.`,
      });
      
      return createdHabit;
    } catch (error) {
      console.error('Error creating habit:', error);
      toast({
        title: "Error",
        description: "Failed to create habit. Please try again.",
        variant: "destructive",
      });
      throw error;
    }
  };

  if (isLoading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto mb-4"></div>
          <p className="text-gray-600">Loading your habit stacks...</p>
        </div>
      </div>
    );
  }

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
                onCreateHabit={handleCreateHabit}
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