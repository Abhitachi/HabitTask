from fastapi import FastAPI, APIRouter, HTTPException
from dotenv import load_dotenv
from starlette.middleware.cors import CORSMiddleware
from motor.motor_asyncio import AsyncIOMotorClient
import os
import logging
from pathlib import Path
from typing import List
from datetime import datetime

# Import models and database
from models import *
from database import (
    db, categories_collection, habits_collection, 
    stacks_collection, progress_collection, init_default_data
)

ROOT_DIR = Path(__file__).parent
load_dotenv(ROOT_DIR / '.env')

# Create the main app without a prefix
app = FastAPI()

# Create a router with the /api prefix
api_router = APIRouter(prefix="/api")

# Initialize default data on startup
@app.on_event("startup")
async def startup_event():
    await init_default_data()

# CATEGORY ENDPOINTS
@api_router.get("/categories", response_model=List[HabitCategory])
async def get_categories():
    """Get all habit categories"""
    try:
        categories = await categories_collection.find().to_list(1000)
        return [HabitCategory(**category) for category in categories]
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@api_router.post("/categories", response_model=HabitCategory)
async def create_category(category: HabitCategoryCreate):
    """Create a new habit category"""
    try:
        category_dict = category.dict()
        category_obj = HabitCategory(**category_dict)
        await categories_collection.insert_one(category_obj.dict())
        return category_obj
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# HABIT ENDPOINTS
@api_router.get("/habits", response_model=List[Habit])
async def get_habits():
    """Get all habits"""
    try:
        habits = await habits_collection.find().to_list(1000)
        return [Habit(**habit) for habit in habits]
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@api_router.post("/habits", response_model=Habit)
async def create_habit(habit: HabitCreate):
    """Create a new habit"""
    try:
        habit_dict = habit.dict()
        habit_obj = Habit(**habit_dict)
        await habits_collection.insert_one(habit_obj.dict())
        return habit_obj
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@api_router.get("/habits/{habit_id}", response_model=Habit)
async def get_habit(habit_id: str):
    """Get a specific habit"""
    try:
        habit = await habits_collection.find_one({"id": habit_id})
        if not habit:
            raise HTTPException(status_code=404, detail="Habit not found")
        return Habit(**habit)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@api_router.put("/habits/{habit_id}", response_model=Habit)
async def update_habit(habit_id: str, habit_update: HabitUpdate):
    """Update a habit"""
    try:
        update_data = {k: v for k, v in habit_update.dict().items() if v is not None}
        if not update_data:
            raise HTTPException(status_code=400, detail="No data to update")
        
        result = await habits_collection.update_one(
            {"id": habit_id}, 
            {"$set": update_data}
        )
        
        if result.matched_count == 0:
            raise HTTPException(status_code=404, detail="Habit not found")
        
        updated_habit = await habits_collection.find_one({"id": habit_id})
        return Habit(**updated_habit)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@api_router.delete("/habits/{habit_id}")
async def delete_habit(habit_id: str):
    """Delete a habit"""
    try:
        result = await habits_collection.delete_one({"id": habit_id})
        if result.deleted_count == 0:
            raise HTTPException(status_code=404, detail="Habit not found")
        return {"message": "Habit deleted successfully"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# HABIT STACK ENDPOINTS
@api_router.get("/stacks", response_model=List[HabitStack])
async def get_stacks():
    """Get all habit stacks"""
    try:
        stacks = await stacks_collection.find().to_list(1000)
        return [HabitStack(**stack) for stack in stacks]
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@api_router.post("/stacks", response_model=HabitStack)
async def create_stack(stack: HabitStackCreate):
    """Create a new habit stack"""
    try:
        stack_dict = stack.dict()
        stack_obj = HabitStack(**stack_dict)
        await stacks_collection.insert_one(stack_obj.dict(by_alias=True))
        
        # Initialize progress data for the new stack
        progress_data = ProgressData(stack_id=stack_obj.id)
        await progress_collection.insert_one(progress_data.dict())
        
        return stack_obj
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@api_router.get("/stacks/{stack_id}", response_model=HabitStack)
async def get_stack(stack_id: str):
    """Get a specific habit stack"""
    try:
        stack = await stacks_collection.find_one({"id": stack_id})
        if not stack:
            raise HTTPException(status_code=404, detail="Stack not found")
        return HabitStack(**stack)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@api_router.put("/stacks/{stack_id}", response_model=HabitStack)
async def update_stack(stack_id: str, stack_update: HabitStackUpdate):
    """Update a habit stack"""
    try:
        update_data = {k: v for k, v in stack_update.dict().items() if v is not None}
        if not update_data:
            raise HTTPException(status_code=400, detail="No data to update")
        
        result = await stacks_collection.update_one(
            {"id": stack_id}, 
            {"$set": update_data}
        )
        
        if result.matched_count == 0:
            raise HTTPException(status_code=404, detail="Stack not found")
        
        updated_stack = await stacks_collection.find_one({"id": stack_id})
        return HabitStack(**updated_stack)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@api_router.delete("/stacks/{stack_id}")
async def delete_stack(stack_id: str):
    """Delete a habit stack"""
    try:
        # Delete the stack
        result = await stacks_collection.delete_one({"id": stack_id})
        if result.deleted_count == 0:
            raise HTTPException(status_code=404, detail="Stack not found")
        
        # Delete associated progress data
        await progress_collection.delete_one({"stack_id": stack_id})
        
        return {"message": "Stack deleted successfully"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# PROGRESS ENDPOINTS
@api_router.get("/progress", response_model=List[ProgressData])
async def get_all_progress():
    """Get progress data for all stacks"""
    try:
        progress_data = await progress_collection.find().to_list(1000)
        return [ProgressData(**progress) for progress in progress_data]
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@api_router.get("/progress/{stack_id}", response_model=ProgressData)
async def get_progress(stack_id: str):
    """Get progress data for a specific stack"""
    try:
        progress = await progress_collection.find_one({"stack_id": stack_id})
        if not progress:
            # Create default progress data if it doesn't exist
            progress_data = ProgressData(stack_id=stack_id)
            await progress_collection.insert_one(progress_data.dict())
            return progress_data
        return ProgressData(**progress)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@api_router.put("/progress/{stack_id}", response_model=ProgressData)
async def update_progress(stack_id: str, progress_update: ProgressDataUpdate):
    """Update progress data for a stack"""
    try:
        update_data = {k: v for k, v in progress_update.dict().items() if v is not None}
        update_data["updated_at"] = datetime.utcnow()
        
        result = await progress_collection.update_one(
            {"stack_id": stack_id}, 
            {"$set": update_data}
        )
        
        if result.matched_count == 0:
            raise HTTPException(status_code=404, detail="Progress data not found")
        
        updated_progress = await progress_collection.find_one({"stack_id": stack_id})
        return ProgressData(**updated_progress)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# TOGGLE COMPLETION ENDPOINT
@api_router.post("/toggle-habit")
async def toggle_habit_completion(toggle_request: ToggleHabitRequest):
    """Toggle completion status of a habit in a stack"""
    try:
        stack_id = toggle_request.stack_id
        habit_id = toggle_request.habit_id
        
        # Get the current stack
        stack = await stacks_collection.find_one({"id": stack_id})
        if not stack:
            raise HTTPException(status_code=404, detail="Stack not found")
        
        # Update the habit completion status
        updated_habits = []
        for habit in stack["habits"]:
            if habit["habitId"] == habit_id:
                habit["completed"] = not habit["completed"]
            updated_habits.append(habit)
        
        # Check if all habits are completed
        all_completed = all(habit["completed"] for habit in updated_habits)
        last_completed = datetime.utcnow() if all_completed else stack.get("lastCompleted")
        
        # Update the stack
        await stacks_collection.update_one(
            {"id": stack_id},
            {
                "$set": {
                    "habits": updated_habits,
                    "lastCompleted": last_completed
                }
            }
        )
        
        # Update progress data
        progress = await progress_collection.find_one({"stack_id": stack_id})
        if progress:
            completed_count = sum(1 for habit in updated_habits if habit["completed"])
            total_count = len(updated_habits)
            completion_rate = completed_count / total_count if total_count > 0 else 0
            
            # Update streak if stack is completed
            current_streak = progress.get("current_streak", 0)
            if all_completed:
                current_streak += 1
            
            longest_streak = max(progress.get("longest_streak", 0), current_streak)
            
            await progress_collection.update_one(
                {"stack_id": stack_id},
                {
                    "$set": {
                        "completion_rate": completion_rate,
                        "current_streak": current_streak,
                        "longest_streak": longest_streak,
                        "updated_at": datetime.utcnow()
                    }
                }
            )
        
        return {"message": "Habit completion toggled successfully"}
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# Health check endpoint
@api_router.get("/")
async def root():
    return {"message": "Habit Stack Builder API is running"}

# Include the router in the main app
app.include_router(api_router)

app.add_middleware(
    CORSMiddleware,
    allow_credentials=True,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

@app.on_event("shutdown")
async def shutdown_db_client():
    pass