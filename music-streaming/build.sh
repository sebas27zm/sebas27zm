#!/bin/bash

# Music Streaming Application Build Script

echo "🎵 Building Music Streaming Application..."

# Create output directory
mkdir -p build/classes

# Compile Java sources
echo "📦 Compiling Java sources..."
javac -d build/classes -sourcepath src/main/java src/main/java/*.java src/main/java/*/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    
    # Create executable JAR
    echo "📦 Creating JAR file..."
    cd build/classes
    jar cfe ../../MusicStreamingApp.jar MusicStreamingApp .
    cd ../..
    
    if [ $? -eq 0 ]; then
        echo "✅ JAR created successfully: MusicStreamingApp.jar"
        echo ""
        echo "🚀 To run the application:"
        echo "   java -jar MusicStreamingApp.jar"
        echo ""
        echo "Or run directly from classes:"
        echo "   java -cp build/classes MusicStreamingApp"
    else
        echo "❌ Failed to create JAR file"
        exit 1
    fi
else
    echo "❌ Compilation failed"
    exit 1
fi