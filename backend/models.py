from pydantic import BaseModel, Field
from typing import List, Optional
from datetime import datetime
import uuid

class HabitCategory(BaseModel):
    id: str = Field(default_factory=lambda: str(uuid.uuid4()))
    name: str
    color: str
    icon: str
    created_at: datetime = Field(default_factory=datetime.utcnow)

class HabitCategoryCreate(BaseModel):
    name: str
    color: str
    icon: str

class Habit(BaseModel):
    id: str = Field(default_factory=lambda: str(uuid.uuid4()))
    name: str
    category: str
    time: int  # in minutes
    description: str
    created_at: datetime = Field(default_factory=datetime.utcnow)

class HabitCreate(BaseModel):
    name: str
    category: str
    time: int
    description: str

class HabitUpdate(BaseModel):
    name: Optional[str] = None
    category: Optional[str] = None
    time: Optional[int] = None
    description: Optional[str] = None

class StackHabit(BaseModel):
    habit_id: str = Field(alias="habitId")
    completed: bool = False

class HabitStack(BaseModel):
    id: str = Field(default_factory=lambda: str(uuid.uuid4()))
    name: str
    habits: List[StackHabit]
    created_at: datetime = Field(default_factory=datetime.utcnow)
    last_completed: Optional[datetime] = Field(alias="lastCompleted", default=None)

class HabitStackCreate(BaseModel):
    name: str
    habits: List[StackHabit]

class HabitStackUpdate(BaseModel):
    name: Optional[str] = None
    habits: Optional[List[StackHabit]] = None
    last_completed: Optional[datetime] = None

class ProgressData(BaseModel):
    id: str = Field(default_factory=lambda: str(uuid.uuid4()))
    stack_id: str
    current_streak: int = 0
    longest_streak: int = 0
    completion_rate: float = 0.0
    last_week_progress: List[bool] = Field(default_factory=lambda: [False] * 7)
    updated_at: datetime = Field(default_factory=datetime.utcnow)

class ProgressDataUpdate(BaseModel):
    current_streak: Optional[int] = None
    longest_streak: Optional[int] = None
    completion_rate: Optional[float] = None
    last_week_progress: Optional[List[bool]] = None

class ToggleHabitRequest(BaseModel):
    stack_id: str
    habit_id: str