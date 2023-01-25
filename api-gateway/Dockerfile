FROM openjdk:17-alpine
COPY target/api-gateway*.jar api-gateway.jar
VOLUME /tmp/api-gateway
EXPOSE 8232:8232
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]