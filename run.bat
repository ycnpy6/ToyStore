@echo off
echo Compiling Toy Store Application...

REM Create output directory
if not exist "out" mkdir out

REM Compile all Java files
javac -d out -cp src/main/java src/main/java/com/toystore/*.java src/main/java/com/toystore/model/*.java src/main/java/com/toystore/data/*.java src/main/java/com/toystore/ui/*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo Starting Toy Store Application...
    java -cp out com.toystore.ToyStoreApplication
) else (
    echo Compilation failed!
    pause
)
