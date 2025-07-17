import React, { useState } from 'react';
import { Card } from './ui/card';
import { Button } from './ui/button';
import { Badge } from './ui/badge';
import { Search, Plus } from 'lucide-react';
import { Input } from './ui/input';
import HabitCard from './HabitCard';

const HabitLibrary = ({ habits, categories, onDragStart }) => {
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [searchTerm, setSearchTerm] = useState('');

  const filteredHabits = habits.filter(habit => {
    const matchesCategory = selectedCategory === 'all' || habit.category === selectedCategory;
    const matchesSearch = habit.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         habit.description.toLowerCase().includes(searchTerm.toLowerCase());
    return matchesCategory && matchesSearch;
  });

  const getCategoryData = (categoryId) => {
    return categories.find(cat => cat.id === categoryId) || { color: '#999', icon: '📋' };
  };

  return (
    <Card className="p-6 h-full overflow-hidden flex flex-col">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-xl font-bold">Habit Library</h2>
        <Button size="sm" className="gap-2">
          <Plus className="w-4 h-4"  />
          Add Habit
        </Button>
      </div>

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