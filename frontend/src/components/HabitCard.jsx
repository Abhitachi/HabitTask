import React from 'react';
import { Card } from './ui/card';
import { Badge } from './ui/badge';
import { Clock, GripVertical } from 'lucide-react';

const HabitCard = ({ 
  habit, 
  category, 
  isDragging = false, 
  isInStack = false,
  isCompleted = false,
  onToggleComplete,
  showDragHandle = true
}) => {
  const categoryData = category || { color: '#999', icon: '📋' };
  
  return (
    <Card 
      className={`
        relative p-4 cursor-pointer transition-all duration-200 hover:shadow-lg
        ${isDragging ? 'opacity-50 rotate-3 scale-105' : ''}
        ${isInStack ? 'border-l-4 bg-gradient-to-r from-gray-50 to-white' : ''}
        ${isCompleted ? 'bg-green-50 border-green-200' : ''}
      `}
      style={{ 
        borderLeftColor: isInStack ? categoryData.color : 'transparent',
        transform: isDragging ? 'translateY(-8px)' : 'none'
      }}
    >
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-3 flex-1">
          {showDragHandle && (
            <GripVertical className="w-4 h-4 text-gray-400" />
          )}
          
          <div className="flex items-center gap-2">
            <span className="text-2xl">{categoryData.icon}</span>
            <div>
              <h3 className={`font-semibold ${isCompleted ? 'line-through text-gray-500' : ''}`}>
                {habit.name}
              </h3>
              <p className="text-sm text-gray-600">{habit.description}</p>
            </div>
          </div>
        </div>
        
        <div className="flex items-center gap-2">
          <Badge 
            variant="secondary" 
            className="text-xs"
            style={{ backgroundColor: `${categoryData.color}20`, color: categoryData.color }}
          >
            <Clock className="w-3 h-3 mr-1" />
            {habit.time}min
          </Badge>
          
          {isInStack && onToggleComplete && (
            <button
              onClick={() => onToggleComplete()}
              className={`
                w-6 h-6 rounded-full border-2 flex items-center justify-center
                transition-all duration-200 hover:scale-110
                ${isCompleted 
                  ? 'bg-green-500 border-green-500 text-white' 
                  : 'border-gray-300 hover:border-green-400'
                }
              `}
            >
              {isCompleted && <span className="text-xs">✓</span>}
            </button>
          )}
        </div>
      </div>
    </Card>
  );
};

export default HabitCard;