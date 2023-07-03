# Stage 1: Build
FROM eclipse-temurin:19.0.2_7-jre-jammy
#MAINTAINER BALOOT_DEVELOPER
ADD target/CA8-1.0-SNAPSHOT.jar CA8.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","CA8.jar"]
