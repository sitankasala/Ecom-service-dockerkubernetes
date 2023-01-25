FROM openjdk:17-alpine
COPY target/product-service*.jar product-service.jar
VOLUME /tmp/product-service
EXPOSE 8083:8083
ENTRYPOINT ["java", "-jar", "product-service.jar"]
