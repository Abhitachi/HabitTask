import React, { useState } from 'react';
import { Card } from './ui/card';
import { Button } from './ui/button';
import { Badge } from './ui/badge';
import { Progress } from './ui/progress';
import { ArrowDown, Play, Pause, MoreVertical, Trash2 } from 'lucide-react';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from './ui/dropdown-menu';
import HabitCard from './HabitCard';

const HabitStack = ({ stack, habits, categories, progressData, onToggleComplete, onDeleteStack }) => {
  const [isActive, setIsActive] = useState(false);
  
  const getHabitData = (habitId) => {
    return habits.find(h => h.id === habitId);
  };

  const getCategoryData = (categoryId) => {
    return categories.find(cat => cat.id === categoryId) || { color: '#999', icon: '📋' };
  };

  const getCompletionPercentage = () => {
    const completed = stack.habits.filter(h => h.completed).length;
    return (completed / stack.habits.length) * 100;
  };

  const getTotalTime = () => {
    return stack.habits.reduce((total, stackHabit) => {
      const habit = getHabitData(stackHabit.habitId);
      return total + (habit ? habit.time : 0);
    }, 0);
  };

  const getStreakInfo = () => {
    return progressData || { currentStreak: 0, longestStreak: 0, completionRate: 0 };
  };

  const isCompleted = () => {
    return stack.habits.every(h => h.completed);
  };

  const handleToggleStack = () => {
    setIsActive(!isActive);
  };

  const formatDate = (date) => {
    if (!date) return 'Never';
    return new Date(date).toLocaleDateString('en-US', { 
      month: 'short', 
      day: 'numeric' 
    });
  };

  const getHabitId = (stackHabit) => {
    // Handle both frontend format (habitId) and backend format (habit_id)
    return stackHabit.habitId || stackHabit.habit_id;
  };

  const streakInfo = getStreakInfo();
  const completionPercentage = getCompletionPercentage();

  return (
    <Card className={`p-6 transition-all duration-300 ${isActive ? 'ring-2 ring-blue-400 shadow-lg' : ''}`}>
      {/* Header */}
      <div className="flex items-center justify-between mb-4">
        <div>
          <h3 className="text-lg font-bold flex items-center gap-2">
            {stack.name}
            {isCompleted() && <span className="text-green-500">✓</span>}
          </h3>
          <p className="text-sm text-gray-600">
            Last completed: {formatDate(stack.lastCompleted)}
          </p>
        </div>
        
        <div className="flex items-center gap-2">
          <Button
            variant={isActive ? "default" : "outline"}
            size="sm"
            onClick={handleToggleStack}
          >
            {isActive ? <Pause className="w-4 h-4" /> : <Play className="w-4 h-4" />}
          </Button>
          
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" size="sm">
                <MoreVertical className="w-4 h-4" />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent>
              <DropdownMenuItem onClick={() => onDeleteStack(stack.id)}>
                <Trash2 className="w-4 h-4 mr-2" />
                Delete Stack
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </div>

      {/* Progress Bar */}
      <div className="mb-4">
        <div className="flex justify-between items-center mb-2">
          <span className="text-sm font-medium">Progress</span>
          <span className="text-sm text-gray-600">{Math.round(completionPercentage)}%</span>
        </div>
        <Progress value={completionPercentage} className="h-2" />
      </div>

      {/* Stats */}
      <div className="grid grid-cols-4 gap-2 mb-4">
        <div className="text-center">
          <div className="text-lg font-bold text-blue-600">{streakInfo.currentStreak}</div>
          <div className="text-xs text-gray-600">Current Streak</div>
        </div>
        <div className="text-center">
          <div className="text-lg font-bold text-green-600">{streakInfo.longestStreak}</div>
          <div className="text-xs text-gray-600">Best Streak</div>
        </div>
        <div className="text-center">
          <div className="text-lg font-bold text-purple-600">{Math.round(streakInfo.completionRate * 100)}%</div>
          <div className="text-xs text-gray-600">Success Rate</div>
        </div>
        <div className="text-center">
          <div className="text-lg font-bold text-orange-600">{getTotalTime()}</div>
          <div className="text-xs text-gray-600">Total mins</div>
        </div>
      </div>

      {/* Habit Chain */}
      <div className={`transition-all duration-300 ${isActive ? 'opacity-100' : 'opacity-70'}`}>
        {stack.habits.map((stackHabit, index) => {
          const habitId = getHabitId(stackHabit);
          const habit = getHabitData(habitId);
          if (!habit) return null;
          
          return (
            <div key={`${habitId}-${index}`} className="relative">
              <HabitCard
                habit={habit}
                category={getCategoryData(habit.category)}
                isInStack={true}
                isCompleted={stackHabit.completed}
                onToggleComplete={() => onToggleComplete(stack.id, habitId)}
                showDragHandle={false}
              />
              
              {/* Arrow between habits */}
              {index < stack.habits.length - 1 && (
                <div className="flex justify-center my-2">
                  <ArrowDown className="w-5 h-5 text-gray-400" />
                </div>
              )}
            </div>
          );
        })}
      </div>
    </Card>
  );
};

export default HabitStack;