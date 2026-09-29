FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/analytics-service-1.0.0.jar app.jar
EXPOSE 8094
ENTRYPOINT ["java","-jar","app.jar"]
