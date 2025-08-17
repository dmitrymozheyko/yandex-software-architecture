# Build stage
FROM gradle:8.14-jdk21 AS builder

# Set working directory
WORKDIR /gradle-build

# Copy the source code
COPY . .

# Build the application
RUN gradle build

# Runtime stage
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the binary from the builder stage
COPY --from=builder /gradle-build/build/libs/temperature-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","./app.jar"]
