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

# Find dependencies in Maven repository
OKHTTP=$(find ~/.m2/repository -name "okhttp-4.12.0.jar" 2>/dev/null | head -1)
GSON=$(find ~/.m2/repository -name "gson-2.10.1.jar" 2>/dev/null | head -1)
OKIO=$(find ~/.m2/repository/com/squareup/okio/okio/3.6.0 -name "okio-3.6.0.jar" 2>/dev/null | head -1)
OKIO_JVM=$(find ~/.m2/repository/com/squareup/okio/okio-jvm/3.6.0 -name "okio-jvm-3.6.0.jar" 2>/dev/null | head -1)
KOTLIN_STDLIB=$(find ~/.m2/repository -path "*/org/jetbrains/kotlin/kotlin-stdlib/*/kotlin-stdlib-*.jar" 2>/dev/null | grep -v "common" | grep -v "jdk" | head -1)

# Build classpath with all dependencies
CP="../target/ai-verifier-java-0.1.0.jar"
[ -n "$OKHTTP" ] && CP="$CP:$OKHTTP"
[ -n "$GSON" ] && CP="$CP:$GSON"
[ -n "$OKIO" ] && CP="$CP:$OKIO"
[ -n "$OKIO_JVM" ] && CP="$CP:$OKIO_JVM"
[ -n "$KOTLIN_STDLIB" ] && CP="$CP:$KOTLIN_STDLIB"

# Run the example
cd examples
javac -cp "$CP" BasicExample.java
java -cp "$CP:." BasicExample
