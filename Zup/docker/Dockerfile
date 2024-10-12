# Stage 1: Use an official Maven image with OpenJDK 17 to build the Java application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy your Maven project files into the container
COPY pom.xml .
COPY src ./src

# Package your application without running tests
RUN mvn clean package -DskipTests

# Stage 2: Use an OpenJDK image to run the application
FROM openjdk:17-jdk

# Declare the build argument
ARG VERSION

# Set the working directory
WORKDIR /app

# Install netcat for the wait-for-it script (using apt-get for Debian-based images)
RUN apt-get update && apt-get install -y netcat

# Copy the packaged application from the build stage
COPY --from=build /app/target/zup-${VERSION}.jar /app/zup-app.jar

# Copy the wait-for-it script
COPY docker/wait-for-it.sh /usr/local/bin/wait-for-it

# Make the wait-for-it script executable
RUN chmod +x /usr/local/bin/wait-for-it

# Expose port 8080 for the Java application
EXPOSE 8080

# Command to run the application, waiting for MySQL service to be ready
ENTRYPOINT ["wait-for-it", "mysqldb:3306", "--", "java", "-Dspring.profiles.active=docker", "-jar", "/app/zup-app.jar"]
