#!/bin/bash
# ──────────────────────────────────────────────────────
#  VastraVeda — Build Script (Linux / macOS)
# ──────────────────────────────────────────────────────

set -e

PROJECT_ROOT="$(cd "$(dirname "$0")" && pwd)"
SRC_DIR="$PROJECT_ROOT/src"
OUT_DIR="$PROJECT_ROOT/out"
JAR_NAME="VastraVeda.jar"
MAIN_CLASS="vastraveda.main.MainApp"

echo ""
echo "🥻  VastraVeda Build Script"
echo "════════════════════════════════════"

# Check Java
if ! command -v javac &> /dev/null; then
    echo "❌  javac not found. Please install JDK 11 or higher."
    echo "    Download from: https://adoptium.net/"
    exit 1
fi

JAVA_VER=$(javac -version 2>&1 | awk '{print $2}' | cut -d'.' -f1)
echo "✔  Java version: $(javac -version 2>&1)"

# Clean
echo ""
echo "🧹  Cleaning output directory..."
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

# Compile
echo "⚙️   Compiling sources..."
find "$SRC_DIR" -name "*.java" > "$PROJECT_ROOT/sources.txt"
javac --release 11 -d "$OUT_DIR" @"$PROJECT_ROOT/sources.txt"
echo "✔  Compilation successful ($(find "$OUT_DIR" -name "*.class" | wc -l | tr -d ' ') classes)"

# Package JAR
echo ""
echo "📦  Packaging JAR..."
mkdir -p "$OUT_DIR/META-INF"
cat > "$OUT_DIR/META-INF/MANIFEST.MF" << EOF
Manifest-Version: 1.0
Main-Class: $MAIN_CLASS
EOF
jar cfm "$PROJECT_ROOT/$JAR_NAME" "$OUT_DIR/META-INF/MANIFEST.MF" -C "$OUT_DIR" .
echo "✔  JAR created: $JAR_NAME ($(du -sh "$PROJECT_ROOT/$JAR_NAME" | cut -f1))"

# Done
echo ""
echo "════════════════════════════════════"
echo "✅  Build complete!"
echo ""
echo "▶   Run with:  java -jar $JAR_NAME"
echo "     OR:       bash run.sh"
echo ""
