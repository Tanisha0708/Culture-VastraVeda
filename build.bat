@echo off
REM ──────────────────────────────────────────────────────
REM  VastraVeda — Build Script (Windows)
REM ──────────────────────────────────────────────────────

echo.
echo 🥻  VastraVeda Build Script (Windows)
echo ════════════════════════════════════

where javac >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ❌  javac not found. Please install JDK 11+ and add to PATH.
    echo     Download: https://adoptium.net/
    pause
    exit /b 1
)

echo ✔  Java found
echo.

REM Clean
echo 🧹  Cleaning...
if exist out rmdir /s /q out
mkdir out

REM Find all .java files
echo ⚙️   Compiling...
for /r src %%f in (*.java) do echo %%f >> sources.txt
javac --release 11 -encoding UTF-8 -d out @sources.txt
if %ERRORLEVEL% NEQ 0 (
    echo ❌  Compilation failed.
    pause
    exit /b 1S
)
echo ✔  Compiled successfully

REM Manifest
echo ⚙️   Creating manifest...
if not exist out\META-INF mkdir out\META-INF
(
  echo Manifest-Version: 1.0
  echo Main-Class: vastraveda.main.MainApp
) > out\META-INF\MANIFEST.MF

REM Package JAR
echo 📦  Packaging JAR...
jar cfm VastraVeda.jar out\META-INF\MANIFEST.MF -C out .
echo ✔  VastraVeda.jar created

echo.
echo ════════════════════════════════════
echo ✅  Build complete!
echo.
echo ▶   Run with:  java -jar VastraVeda.jar
echo      OR:       run.bat
echo.
pause
