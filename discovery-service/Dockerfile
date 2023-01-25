FROM openjdk:17-alpine as builder
COPY target/discovery-service*.jar discovery-service.jar
VOLUME /tmp/discovery-service
EXPOSE 8761:8761
ENTRYPOINT ["java", "-jar", "discovery-service.jar"]