#!/bin/bash

# Quick script to build and run the Java SDK example

set -e

echo "🔨 Building AI Verifier Java SDK..."
mvn clean package -DskipTests

echo ""
echo "📦 Build complete!"
echo ""
echo "🚀 Running example..."
echo "   (Make sure AI Verifier Core is running on http://localhost:8080)"
echo ""

# Run the example
cd examples
javac -cp ../target/ai-verifier-java-0.1.0.jar BasicExample.java
java -cp ../target/ai-verifier-java-0.1.0.jar:. BasicExample
