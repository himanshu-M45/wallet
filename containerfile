# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and the build.gradle file
COPY gradlew build.gradle /app/
COPY gradle /app/gradle

# Copy the source code
COPY src /app/src

# Copy the application properties
COPY src/main/resources/application.properties /app/src/main/resources/

# Grant execution rights to the Gradle wrapper
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build -x test

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "build/libs/your-application-name.jar"]