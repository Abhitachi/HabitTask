#!/bin/bash

# Java Backend Startup Script for Habit Stack Builder

echo "Starting Java Backend for Habit Stack Builder..."

# Set environment variables
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-arm64
export PATH=$JAVA_HOME/bin:$PATH

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install Java 17 or later."
    exit 1
fi

# Load environment variables
if [ -f .env ]; then
    export $(cat .env | xargs)
fi

# Set default values if not provided
export MONGO_URL=${MONGO_URL:-mongodb://localhost:27017}
export DB_NAME=${DB_NAME:-habitstack}

echo "Java Version: $(java -version 2>&1 | head -1)"
echo "MongoDB URL: $MONGO_URL"
echo "Database Name: $DB_NAME"

# Try to build with Maven if available
if command -v mvn &> /dev/null; then
    echo "Building with Maven..."
    mvn clean package -DskipTests
    if [ $? -eq 0 ]; then
        echo "Maven build successful. Starting application..."
        java -jar target/habit-stack-builder-1.0.0.jar
    else
        echo "Maven build failed. Please check the build configuration."
        exit 1
    fi
else
    echo "Maven is not available. Cannot build the Java application."
    echo "Please install Maven or use the Python FastAPI backend instead."
    exit 1
fi