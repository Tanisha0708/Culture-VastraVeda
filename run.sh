#!/bin/bash
# ──────────────────────────────────────────────────────
#  VastraVeda — Run Script (Linux / macOS)
# ──────────────────────────────────────────────────────

PROJECT_ROOT="$(cd "$(dirname "$0")" && pwd)"
JAR="$PROJECT_ROOT/VastraVeda.jar"

if [ ! -f "$JAR" ]; then
    echo "⚠️  VastraVeda.jar not found. Building first..."
    bash "$PROJECT_ROOT/build.sh"
fi

echo "🚀  Launching VastraVeda..."
java -jar "$JAR"
