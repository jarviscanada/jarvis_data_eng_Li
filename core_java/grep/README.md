# Introduction
This project implements a simplified version of the Linux grep command in Java. The application searches files in a directory recursively and outputs
lines that match a user-provided regular expression. The project contains two implementations: one using traditional loops and another using Java 8 
Lambda and Stream APIs for functional programming. The application was developed using Core Java, Java Collections, Regex, SLF4J logging, Maven, and Docker.
Development and testing were performed using an IDE and Linux command-line tools.

# Quick Start
Build the project:

`maven clean package`

Run application:

java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepLambdaImp "IllegalArgumentException" ./src /tmp/grep.out

View Results:

`cat /tmp/grep.out`

# Implemenation
## Pseudocode
```
1. Get all files from the root directory
2. Convert file list to stream
3. For each file, read all lines
4. Flatten all lines into one stream
5. Filter lines that match the regex pattern
6. Collect matching lines into a list
7. Write the list of matching lines to the output file
```

## Performance Issue
Currently, the grep app implementation stores all the matched lines in memory before finally writing to the output file. When processing big directories
or a large number of files, this could lead to high memory usage and `OutOfMemoryError`. A workaround to this would be to use streaming I/O, which 
would write the lines to the file as they are being processed instead of one huge batch or returning streams instead of lists, so we don't load all the data
into memory at once.


# Test
The application was tested manually using sample directories containing multiple text files. Various regex patterns were used to verify correct matching behaviour. 
After running the application, the output file was checked using the cat command and compared with results from the Linux grep command to ensure accuracy.

Example input:
`.*IllegalArgumentException.* ./src /tmp/grep.out`

# Deployment
The application was containerized using Docker to simplify deployment and ensure a consistent runtime environment. A Docker image was created 
using a Dockerfile that installs Java, copies the compiled jar file into the container, and runs the application with user-provided arguments.

# Improvement
1. Improve memory efficiency using streaming I/O or returning streams
2. Using Java NIO instead of recursively going through directories
3. Define more edge cases for better code integrity. 




