import React, { useState } from 'react';
import { Card } from './ui/card';
import { Button } from './ui/button';
import { Badge } from './ui/badge';
import { Search, Plus, X } from 'lucide-react';
import { Input } from './ui/input';
import { Label } from './ui/label';
import { Textarea } from './ui/textarea';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from './ui/select';
import HabitCard from './HabitCard';

const HabitLibrary = ({ habits, categories, onDragStart, onCreateHabit }) => {
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [searchTerm, setSearchTerm] = useState('');
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [newHabit, setNewHabit] = useState({
    name: '',
    category: '',
    time: '',
    description: ''
  });

  const filteredHabits = habits.filter(habit => {
    const matchesCategory = selectedCategory === 'all' || habit.category === selectedCategory;
    const matchesSearch = habit.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         habit.description.toLowerCase().includes(searchTerm.toLowerCase());
    return matchesCategory && matchesSearch;
  });

  const getCategoryData = (categoryId) => {
    return categories.find(cat => cat.id === categoryId) || { color: '#999', icon: '📋' };
  };

  const handleCreateHabit = async (e) => {
    e.preventDefault();
    if (!newHabit.name || !newHabit.category || !newHabit.time || !newHabit.description) {
      return;
    }
    
    try {
      await onCreateHabit({
        name: newHabit.name,
        category: newHabit.category,
        time: parseInt(newHabit.time),
        description: newHabit.description
      });
      
      setNewHabit({ name: '', category: '', time: '', description: '' });
      setShowCreateForm(false);
    } catch (error) {
      console.error('Error creating habit:', error);
    }
  };

  const resetForm = () => {
    setNewHabit({ name: '', category: '', time: '', description: '' });
    setShowCreateForm(false);
  };

  return (
    <Card className="p-6 h-full overflow-hidden flex flex-col">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-xl font-bold">Habit Library</h2>
        <Button size="sm" className="gap-2" onClick={() => setShowCreateForm(true)}>
          <Plus className="w-4 h-4" />
          Add Habit
        </Button>
      </div>

      {/* Create Habit Form */}
      {showCreateForm && (
        <Card className="p-4 mb-4 border-2 border-blue-200 bg-blue-50">
          <form onSubmit={handleCreateHabit} className="space-y-3">
            <div className="flex items-center justify-between mb-2">
              <h3 className="font-semibold">Create New Habit</h3>
              <Button type="button" variant="ghost" size="sm" onClick={resetForm}>
                <X className="w-4 h-4" />
              </Button>
            </div>
            
            <div>
              <Label htmlFor="habit-name">Habit Name</Label>
              <Input
                id="habit-name"
                placeholder="e.g., Morning Meditation"
                value={newHabit.name}
                onChange={(e) => setNewHabit({...newHabit, name: e.target.value})}
                required
              />
            </div>
            
            <div>
              <Label htmlFor="habit-category">Category</Label>
              <Select value={newHabit.category} onValueChange={(value) => setNewHabit({...newHabit, category: value})}>
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
              <Label htmlFor="habit-time">Time (minutes)</Label>
              <Input
                id="habit-time"
                type="number"
                placeholder="e.g., 10"
                value={newHabit.time}
                onChange={(e) => setNewHabit({...newHabit, time: e.target.value})}
                min="1"
                required
              />
            </div>
            
            <div>
              <Label htmlFor="habit-description">Description</Label>
              <Textarea
                id="habit-description"
                placeholder="Brief description of the habit"
                value={newHabit.description}
                onChange={(e) => setNewHabit({...newHabit, description: e.target.value})}
                rows={2}
                required
              />
            </div>
            
            <div className="flex gap-2">
              <Button type="submit" size="sm" className="flex-1">
                Create Habit
              </Button>
              <Button type="button" variant="outline" size="sm" onClick={resetForm}>
                Cancel
              </Button>
            </div>
          </form>
        </Card>
      )}

      {/* Search */}
      <div className="relative mb-4">
        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4" />
        <Input
          placeholder="Search habits..."
          className="pl-10"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      {/* Category Filter */}
      <div className="flex flex-wrap gap-2 mb-4">
        <Badge
          variant={selectedCategory === 'all' ? 'default' : 'secondary'}
          className="cursor-pointer hover:scale-105 transition-transform"
          onClick={() => setSelectedCategory('all')}
        >
          All ({habits.length})
        </Badge>
        {categories.map(category => (
          <Badge
            key={category.id}
            variant={selectedCategory === category.id ? 'default' : 'secondary'}
            className="cursor-pointer hover:scale-105 transition-transform gap-1"
            onClick={() => setSelectedCategory(category.id)}
            style={{ 
              backgroundColor: selectedCategory === category.id ? category.color : 'transparent',
              color: selectedCategory === category.id ? 'white' : category.color,
              borderColor: category.color
            }}
          >
            <span>{category.icon}</span>
            {category.name} ({habits.filter(h => h.category === category.id).length})
          </Badge>
        ))}
      </div>

      {/* Habits List */}
      <div className="flex-1 overflow-y-auto space-y-3">
        {filteredHabits.map(habit => (
          <div
            key={habit.id}
            draggable
            onDragStart={(e) => onDragStart(e, habit)}
            className="hover:scale-[1.02] transition-transform cursor-grab active:cursor-grabbing"
          >
            <HabitCard
              habit={habit}
              category={getCategoryData(habit.category)}
              showDragHandle={true}
            />
          </div>
        ))}
        
        {filteredHabits.length === 0 && (
          <div className="text-center py-8 text-gray-500">
            <p>No habits found</p>
            <p className="text-sm">Try adjusting your search or filters</p>
          </div>
        )}
      </div>
    </Card>
  );
};

export default HabitLibrary;