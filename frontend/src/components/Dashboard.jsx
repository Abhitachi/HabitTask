import React, { useState } from 'react';
import { Card } from './ui/card';
import { Button } from './ui/button';
import { Badge } from './ui/badge';
import { Plus, TrendingUp, Calendar, Target } from 'lucide-react';
import HabitLibrary from './HabitLibrary';
import ChainBuilder from './ChainBuilder';
import HabitStack from './HabitStack';
import { useToast } from '../hooks/use-toast';

const Dashboard = ({ 
  habits, 
  categories, 
  habitStacks, 
  progressData, 
  onSaveStack, 
  onDeleteStack,
  onToggleComplete,
  onCreateHabit
}) => {
  const { toast } = useToast();
  const [draggedHabit, setDraggedHabit] = useState(null);
  const [showHabitLibrary, setShowHabitLibrary] = useState(false);

  const handleDragStart = (e, habit) => {
    setDraggedHabit(habit);
    e.dataTransfer.setData('text/plain', JSON.stringify(habit));
  };

  const handleSaveStack = (newStack) => {
    onSaveStack(newStack);
  };

  const handleClearStack = () => {
    toast({
      title: "Stack Cleared",
      description: "Chain builder has been reset.",
    });
  };

  const getTotalStacks = () => habitStacks.length;
  
  const getCompletedStacks = () => habitStacks.filter(stack => 
    stack.habits.every(h => h.completed)
  ).length;
  
  const getActiveStacks = () => habitStacks.filter(stack => 
    stack.habits.some(h => h.completed) && !stack.habits.every(h => h.completed)
  ).length;

  const getOverallProgress = () => {
    if (habitStacks.length === 0) return 0;
    const totalHabits = habitStacks.reduce((sum, stack) => sum + stack.habits.length, 0);
    const completedHabits = habitStacks.reduce((sum, stack) => 
      sum + stack.habits.filter(h => h.completed).length, 0
    );
    return totalHabits > 0 ? (completedHabits / totalHabits) * 100 : 0;
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-4xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent mb-2">
            Habit Stack Builder
          </h1>
          <p className="text-gray-600 text-lg">
            Build powerful habit chains by linking new habits to existing routines
          </p>
        </div>

        {/* Stats Overview */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
          <Card className="p-4 bg-gradient-to-r from-blue-500 to-blue-600 text-white">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-blue-100 text-sm">Total Stacks</p>
                <p className="text-2xl font-bold">{getTotalStacks()}</p>
              </div>
              <Target className="w-8 h-8 text-blue-200" />
            </div>
          </Card>
          
          <Card className="p-4 bg-gradient-to-r from-green-500 to-green-600 text-white">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-green-100 text-sm">Completed</p>
                <p className="text-2xl font-bold">{getCompletedStacks()}</p>
              </div>
              <TrendingUp className="w-8 h-8 text-green-200" />
            </div>
          </Card>
          
          <Card className="p-4 bg-gradient-to-r from-orange-500 to-orange-600 text-white">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-orange-100 text-sm">In Progress</p>
                <p className="text-2xl font-bold">{getActiveStacks()}</p>
              </div>
              <Calendar className="w-8 h-8 text-orange-200" />
            </div>
          </Card>
          
          <Card className="p-4 bg-gradient-to-r from-purple-500 to-purple-600 text-white">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-purple-100 text-sm">Overall Progress</p>
                <p className="text-2xl font-bold">{Math.round(getOverallProgress())}%</p>
              </div>
              <TrendingUp className="w-8 h-8 text-purple-200" />
            </div>
          </Card>
        </div>

        {/* Main Content */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Habit Library */}
          <div className="lg:col-span-1">
            <HabitLibrary
              habits={habits}
              categories={categories}
              onDragStart={handleDragStart}
              onCreateHabit={onCreateHabit}
            />
          </div>

          {/* Chain Builder */}
          <div className="lg:col-span-1">
            <ChainBuilder
              categories={categories}
              onSaveStack={handleSaveStack}
              onClearStack={handleClearStack}
            />
          </div>

          {/* Habit Stacks */}
          <div className="lg:col-span-1">
            <Card className="p-6 h-full">
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-xl font-bold">My Habit Stacks</h2>
                <Badge variant="outline">
                  {habitStacks.length} stacks
                </Badge>
              </div>
              
              <div className="space-y-4 overflow-y-auto max-h-[600px]">
                {habitStacks.map(stack => (
                  <HabitStack
                    key={stack.id}
                    stack={stack}
                    habits={habits}
                    categories={categories}
                    progressData={progressData[stack.id]}
                    onToggleComplete={onToggleComplete}
                    onDeleteStack={onDeleteStack}
                  />
                ))}
                
                {habitStacks.length === 0 && (
                  <div className="text-center py-8 text-gray-500">
                    <Plus className="w-12 h-12 mx-auto mb-2 opacity-50" />
                    <p className="text-lg font-medium">No habit stacks yet</p>
                    <p className="text-sm">Create your first stack using the chain builder</p>
                  </div>
                )}
              </div>
            </Card>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;