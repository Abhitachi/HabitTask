import React, { useState } from 'react';
import { Card } from './ui/card';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Badge } from './ui/badge';
import { Label } from './ui/label';
import { Textarea } from './ui/textarea';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from './ui/select';
import { ArrowDown, Save, Trash2, Plus, X } from 'lucide-react';
import HabitCard from './HabitCard';

const ChainBuilder = ({ categories, onSaveStack, onClearStack, onCreateHabit }) => {
  const [stackName, setStackName] = useState('');
  const [currentStack, setCurrentStack] = useState([]);
  const [isDragOver, setIsDragOver] = useState(false);
  const [showAddTaskForm, setShowAddTaskForm] = useState(false);
  const [newTask, setNewTask] = useState({
    name: '',
    category: '',
    time: '',
    description: ''
  });

  const handleDragOver = (e) => {
    e.preventDefault();
    setIsDragOver(true);
  };

  const handleDragLeave = (e) => {
    e.preventDefault();
    setIsDragOver(false);
  };

  const handleDrop = (e) => {
    e.preventDefault();
    setIsDragOver(false);
    
    const habitData = JSON.parse(e.dataTransfer.getData('text/plain'));
    
    // Check if habit already exists in stack
    if (!currentStack.find(item => item.id === habitData.id)) {
      setCurrentStack([...currentStack, habitData]);
    }
  };

  const removeFromStack = (habitId) => {
    setCurrentStack(currentStack.filter(habit => habit.id !== habitId));
  };

  const moveHabit = (fromIndex, toIndex) => {
    const newStack = [...currentStack];
    const [movedHabit] = newStack.splice(fromIndex, 1);
    newStack.splice(toIndex, 0, movedHabit);
    setCurrentStack(newStack);
  };

  const getTotalTime = () => {
    return currentStack.reduce((total, habit) => total + habit.time, 0);
  };

  const getCategoryData = (categoryId) => {
    return categories.find(cat => cat.id === categoryId) || { color: '#999', icon: '📋' };
  };

  const handleSave = () => {
    if (stackName.trim() && currentStack.length > 0) {
      const newStack = {
        name: stackName,
        habits: currentStack.map(habit => ({ habitId: habit.id, completed: false }))
      };
      onSaveStack(newStack);
      setStackName('');
      setCurrentStack([]);
    }
  };

  const handleClear = () => {
    setCurrentStack([]);
    setStackName('');
    onClearStack();
  };

  const handleCreateTask = async (e) => {
    e.preventDefault();
    if (!newTask.name || !newTask.category || !newTask.time || !newTask.description) {
      return;
    }
    
    try {
      const createdHabit = await onCreateHabit({
        name: newTask.name,
        category: newTask.category,
        time: parseInt(newTask.time),
        description: newTask.description
      });
      
      // Add the newly created habit to the stack
      setCurrentStack([...currentStack, createdHabit]);
      
      // Reset form
      setNewTask({ name: '', category: '', time: '', description: '' });
      setShowAddTaskForm(false);
    } catch (error) {
      console.error('Error creating task:', error);
    }
  };

  const resetTaskForm = () => {
    setNewTask({ name: '', category: '', time: '', description: '' });
    setShowAddTaskForm(false);
  };

  return (
    <Card className="p-6 h-full flex flex-col">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-xl font-bold">Chain Builder</h2>
        <div className="flex gap-2">
          <Button
            variant="outline"
            size="sm"
            onClick={() => setShowAddTaskForm(true)}
            className="gap-1"
          >
            <Plus className="w-4 h-4" />
            Add Task
          </Button>
          <Button
            variant="outline"
            size="sm"
            onClick={handleClear}
            disabled={currentStack.length === 0}
          >
            <Trash2 className="w-4 h-4 mr-1" />
            Clear
          </Button>
          <Button
            size="sm"
            onClick={handleSave}
            disabled={!stackName.trim() || currentStack.length === 0}
          >
            <Save className="w-4 h-4 mr-1" />
            Save Stack
          </Button>
        </div>
      </div>

      {/* Stack Name Input */}
      <div className="mb-4">
        <Input
          placeholder="Enter stack name..."
          value={stackName}
          onChange={(e) => setStackName(e.target.value)}
          className="w-full"
        />
      </div>

      {/* Add Task Form */}
      {showAddTaskForm && (
        <Card className="p-4 mb-4 border-2 border-green-200 bg-green-50">
          <form onSubmit={handleCreateTask} className="space-y-3">
            <div className="flex items-center justify-between mb-2">
              <h3 className="font-semibold">Add New Task</h3>
              <Button type="button" variant="ghost" size="sm" onClick={resetTaskForm}>
                <X className="w-4 h-4" />
              </Button>
            </div>
            
            <div>
              <Label htmlFor="task-name">Task Name</Label>
              <Input
                id="task-name"
                placeholder="e.g., Morning Workout"
                value={newTask.name}
                onChange={(e) => setNewTask({...newTask, name: e.target.value})}
                required
              />
            </div>
            
            <div>
              <Label htmlFor="task-category">Category</Label>
              <Select value={newTask.category} onValueChange={(value) => setNewTask({...newTask, category: value})}>
                <SelectTrigger>
                  <SelectValue placeholder="Select category" />
                </SelectTrigger>
                <SelectContent>
                  {categories.map(category => (
                    <SelectItem key={category.id} value={category.id}>
                      <span className="flex items-center gap-2">
                        {category.icon} {category.name}
                      </span>
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>
            
            <div>
              <Label htmlFor="task-time">Time (minutes)</Label>
              <Input
                id="task-time"
                type="number"
                placeholder="e.g., 15"
                value={newTask.time}
                onChange={(e) => setNewTask({...newTask, time: e.target.value})}
                min="1"
                required
              />
            </div>
            
            <div>
              <Label htmlFor="task-description">Description</Label>
              <Textarea
                id="task-description"
                placeholder="Brief description of the task"
                value={newTask.description}
                onChange={(e) => setNewTask({...newTask, description: e.target.value})}
                rows={2}
                required
              />
            </div>
            
            <div className="flex gap-2">
              <Button type="submit" size="sm" className="flex-1">
                Add to Stack
              </Button>
              <Button type="button" variant="outline" size="sm" onClick={resetTaskForm}>
                Cancel
              </Button>
            </div>
          </form>
        </Card>
      )}

      {/* Drop Zone */}
      <div
        className={`
          flex-1 border-2 border-dashed rounded-lg p-4 transition-all duration-200
          ${isDragOver 
            ? 'border-blue-400 bg-blue-50' 
            : 'border-gray-300 hover:border-gray-400'
          }
          ${currentStack.length === 0 ? 'flex items-center justify-center' : ''}
        `}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
      >
        {currentStack.length === 0 ? (
          <div className="text-center text-gray-500">
            <Plus className="w-12 h-12 mx-auto mb-2 opacity-50" />
            <p className="text-lg font-medium">Drop habits here to build your stack</p>
            <p className="text-sm">Drag habits from the library to create your chain</p>
          </div>
        ) : (
          <div className="space-y-3">
            {currentStack.map((habit, index) => (
              <div key={`${habit.id}-${index}`} className="relative">
                <div className="group relative">
                  <HabitCard
                    habit={habit}
                    category={getCategoryData(habit.category)}
                    isInStack={true}
                    showDragHandle={false}
                  />
                  
                  {/* Remove button */}
                  <button
                    onClick={() => removeFromStack(habit.id)}
                    className="absolute top-2 right-2 w-6 h-6 bg-red-500 text-white rounded-full opacity-0 group-hover:opacity-100 transition-opacity hover:bg-red-600 flex items-center justify-center"
                  >
                    <Trash2 className="w-3 h-3" />
                  </button>
                </div>
                
                {/* Arrow between habits */}
                {index < currentStack.length - 1 && (
                  <div className="flex justify-center my-2">
                    <ArrowDown className="w-6 h-6 text-gray-400" />
                  </div>
                )}
              </div>
            ))}
          </div>
        )}
      </div>

      {/* Stack Summary */}
      {currentStack.length > 0 && (
        <div className="mt-4 p-3 bg-gray-50 rounded-lg">
          <div className="flex justify-between items-center text-sm">
            <span className="font-medium">Stack Summary:</span>
            <div className="flex gap-4">
              <Badge variant="outline">
                {currentStack.length} habits
              </Badge>
              <Badge variant="outline">
                {getTotalTime()} min total
              </Badge>
            </div>
          </div>
        </div>
      )}
    </Card>
  );
};

export default ChainBuilder;