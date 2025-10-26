#!/bin/bash
echo "Compiling Toy Store Application..."

# Create output directory
mkdir -p out

# Compile all Java files
javac -d out -cp src/main/java src/main/java/com/toystore/*.java src/main/java/com/toystore/model/*.java src/main/java/com/toystore/data/*.java src/main/java/com/toystore/ui/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Starting Toy Store Application..."
    java -cp out com.toystore.ToyStoreApplication
else
    echo "Compilation failed!"
    exit 1
fi

