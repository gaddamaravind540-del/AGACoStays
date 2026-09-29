FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/attendance-service-1.0.0.jar app.jar
EXPOSE 8092
ENTRYPOINT ["java","-jar","app.jar"]
