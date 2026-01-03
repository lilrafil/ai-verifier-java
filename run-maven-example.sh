#!/bin/bash

# Simple script to run the example using Maven with all dependencies

set -e

echo "🚀 Running BasicExample with Maven..."
echo "   (Make sure AI Verifier Core is running on http://localhost:8080)"
echo ""

# Use Maven exec plugin to run with all dependencies
mvn exec:java -Dexec.mainClass="BasicExample" -Dexec.classpathScope="compile" -Dexec.cleanupDaemonThreads=false
