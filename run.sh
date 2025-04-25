#!/bin/bash

echo "Cleaning and compiling the project..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Running the application..."
    java -cp target/classes:$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q -DincludeScope=runtime) application.Program
else
    echo "Compilation failed."
fi

