# Use Maven 3.8.5 with JDK 17 for the build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY ../pom.xml .
COPY ../src ./src
RUN mvn clean package -DskipTests

# Use OpenJDK 17 for the runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/zup-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
