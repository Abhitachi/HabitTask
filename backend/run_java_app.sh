#!/bin/bash

# Script to run the Java Spring Boot application
echo "Starting Habit Stack Builder Java Backend..."

# Set environment variables
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-arm64
export PATH=$JAVA_HOME/bin:$PATH

# Load environment variables from .env file
if [ -f .env ]; then
    export $(cat .env | sed 's/#.*//g' | xargs)
fi

# Check Java version
echo "Java version: $(java -version 2>&1 | head -1)"

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Maven not found. Please install Maven first."
    exit 1
fi

# Clean and compile the project
echo "Compiling the project..."
mvn clean compile -q

if [ $? -ne 0 ]; then
    echo "Compilation failed. Please check the code."
    exit 1
fi

# Package the application
echo "Packaging the application..."
mvn package -DskipTests -q

if [ $? -ne 0 ]; then
    echo "Packaging failed. Please check the code."
    exit 1
fi

# Run the application
echo "Starting the application..."
java -jar target/habit-stack-builder-1.0.0.jar

echo "Application stopped."