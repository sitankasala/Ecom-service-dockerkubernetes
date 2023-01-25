FROM openjdk:17-alpine
COPY target/order-service*.jar order-service.jar
VOLUME /tmp/order-service
EXPOSE 8081:8081
ENTRYPOINT ["java", "-jar", "order-service.jar"]