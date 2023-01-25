FROM openjdk:17-alpine
COPY target/inventory-service*.jar inventory-service.jar
VOLUME /tmp/inventory-service
EXPOSE 8082:8082
ENTRYPOINT ["java", "-jar", "inventory-service.jar"]