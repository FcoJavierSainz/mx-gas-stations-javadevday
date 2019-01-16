FROM openjdk:8-jdk-alpine
MAINTAINER SETS-ESA Team

COPY build/libs/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

EXPOSE 8082