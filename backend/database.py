from motor.motor_asyncio import AsyncIOMotorClient
import os
from dotenv import load_dotenv

load_dotenv()

# MongoDB connection
mongo_url = os.environ['MONGO_URL']
client = AsyncIOMotorClient(mongo_url)
db = client[os.environ['DB_NAME']]

# Collections
categories_collection = db.habit_categories
habits_collection = db.habits
stacks_collection = db.habit_stacks
progress_collection = db.progress_data

async def init_default_data():
    """Initialize default categories and habits if they don't exist"""
    
    # Check if categories already exist
    if await categories_collection.count_documents({}) == 0:
        default_categories = [
            {'id': 'morning', 'name': 'Morning Routine', 'color': '#FFB800', 'icon': '🌅'},
            {'id': 'exercise', 'name': 'Exercise', 'color': '#FF6B6B', 'icon': '💪'},
            {'id': 'work', 'name': 'Work', 'color': '#4ECDC4', 'icon': '💼'},
            {'id': 'evening', 'name': 'Evening Routine', 'color': '#A8E6CF', 'icon': '🌙'},
            {'id': 'health', 'name': 'Health', 'color': '#88D8B0', 'icon': '🏥'},
            {'id': 'learning', 'name': 'Learning', 'color': '#FFD93D', 'icon': '📚'}
        ]
        
        await categories_collection.insert_many(default_categories)
        print("Default categories inserted")
    
    # Check if habits already exist
    if await habits_collection.count_documents({}) == 0:
        default_habits = [
            # Morning Routine
            {'id': '1', 'name': 'Brush Teeth', 'category': 'morning', 'time': 3, 'description': 'Clean teeth and gums'},
            {'id': '2', 'name': 'Drink Water', 'category': 'morning', 'time': 1, 'description': '16oz of water'},
            {'id': '3', 'name': 'Make Bed', 'category': 'morning', 'time': 2, 'description': 'Tidy up bedroom'},
            {'id': '4', 'name': 'Make Coffee', 'category': 'morning', 'time': 5, 'description': 'Brew morning coffee'},
            {'id': '5', 'name': 'Check Weather', 'category': 'morning', 'time': 1, 'description': 'Plan outfit for day'},
            
            # Exercise
            {'id': '6', 'name': '10 Push-ups', 'category': 'exercise', 'time': 2, 'description': 'Quick strength training'},
            {'id': '7', 'name': '5-min Stretch', 'category': 'exercise', 'time': 5, 'description': 'Basic stretching routine'},
            {'id': '8', 'name': 'Go for Walk', 'category': 'exercise', 'time': 15, 'description': '15-minute walk outside'},
            {'id': '9', 'name': 'Planks', 'category': 'exercise', 'time': 3, 'description': '1-minute plank hold'},
            {'id': '10', 'name': 'Jumping Jacks', 'category': 'exercise', 'time': 2, 'description': '20 jumping jacks'},
            
            # Work
            {'id': '11', 'name': 'Check Email', 'category': 'work', 'time': 10, 'description': 'Review and respond to emails'},
            {'id': '12', 'name': 'Review To-Do', 'category': 'work', 'time': 3, 'description': 'Plan daily tasks'},
            {'id': '13', 'name': 'Deep Work Block', 'category': 'work', 'time': 90, 'description': 'Focused work session'},
            {'id': '14', 'name': 'Team Standup', 'category': 'work', 'time': 15, 'description': 'Daily team meeting'},
            
            # Evening Routine
            {'id': '15', 'name': 'Prepare Clothes', 'category': 'evening', 'time': 5, 'description': 'Lay out tomorrow\'s outfit'},
            {'id': '16', 'name': 'Journal', 'category': 'evening', 'time': 10, 'description': 'Write daily reflections'},
            {'id': '17', 'name': 'Read Book', 'category': 'evening', 'time': 20, 'description': 'Read before bed'},
            {'id': '18', 'name': 'Phone Away', 'category': 'evening', 'time': 1, 'description': 'Put phone in another room'},
            
            # Health
            {'id': '19', 'name': 'Take Vitamins', 'category': 'health', 'time': 1, 'description': 'Daily supplement routine'},
            {'id': '20', 'name': 'Meditate', 'category': 'health', 'time': 10, 'description': '10-minute mindfulness'},
            {'id': '21', 'name': 'Floss', 'category': 'health', 'time': 2, 'description': 'Daily flossing routine'},
            
            # Learning
            {'id': '22', 'name': 'Duolingo', 'category': 'learning', 'time': 15, 'description': 'Language learning practice'},
            {'id': '23', 'name': 'Watch Tutorial', 'category': 'learning', 'time': 20, 'description': 'Educational video'},
            {'id': '24', 'name': 'Practice Code', 'category': 'learning', 'time': 30, 'description': 'Coding practice'}
        ]
        
        await habits_collection.insert_many(default_habits)
        print("Default habits inserted")