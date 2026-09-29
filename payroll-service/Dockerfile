FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/payroll-service-1.0.0.jar app.jar
EXPOSE 8093
ENTRYPOINT ["java","-jar","app.jar"]
