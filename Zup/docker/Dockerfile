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

# Install MySQL server
RUN apt-get update && \
    apt-get install -y mysql-server && \
    apt-get clean

# Install Supervisord to manage multiple services
RUN apt-get update && \
    apt-get install -y supervisor && \
    apt-get clean

# Create the directory for supervisor configuration
RUN mkdir -p /var/log/supervisor

# Declare the build argument
ARG VERSION

# Set the working directory
WORKDIR /app

# Install netcat for the wait-for-it script (using apt-get for Debian-based images)
RUN apt-get update && apt-get install -y netcat

# Copy the packaged application from the build stage
COPY --from=build /app/target/zup-${VERSION}.jar /app/zup-app.jar

# Configure MySQL (set root password and database creation)
ENV MYSQL_ROOT_PASSWORD=123456
ENV MYSQL_DATABASE=zup

# Create a supervisord configuration file to manage both MySQL and the application
COPY docker/supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# Copy init SQL script for MySQL
COPY docker/path/to/init.sql /docker-entrypoint-initdb.d/

# Expose ports for MySQL and the application
EXPOSE 3306 8080

# Command to run the application, waiting for MySQL service to be ready
ENTRYPOINT ["/usr/bin/supervisord", "mysqldb:3306", "--", "java", "-Dspring.profiles.active=docker", "-jar", "/app/zup-app.jar"]
