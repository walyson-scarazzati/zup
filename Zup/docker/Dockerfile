# Use an official Maven image as a parent image
FROM maven:3.8.6-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy your Maven project files into the container
COPY pom.xml .
COPY src ./src

# Package your application
RUN mvn clean package -DskipTests

# Use an OpenJDK image as the base image for running the application
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy the packaged application from the build stage
COPY --from=build /app/target/zup-0.0.1-SNAPSHOT.jar /app/zup-app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/zup-app.jar"]
