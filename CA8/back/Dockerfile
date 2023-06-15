# Multi-stage

# Stage 1: Build
FROM maven:3.8.1-openjdk-17 AS MAVEN
MAINTAINER BALOOT_DEVELOPER
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn -f pom.xml clean package



## Stage 2: Package and run
FROM openjdk:19
WORKDIR /app
COPY --from=MAVEN /build/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java" , "-jar" , "app.jar"]
