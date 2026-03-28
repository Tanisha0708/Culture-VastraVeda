@echo off
REM ──────────────────────────────────────────────────────
REM  VastraVeda — Run Script (Windows)
REM ──────────────────────────────────────────────────────

if not exist VastraVeda.jar (
    echo ⚠️  VastraVeda.jar not found. Running build first...
    call build.bat
)

echo 🚀  Launching VastraVeda...
java -jar VastraVeda.jar
