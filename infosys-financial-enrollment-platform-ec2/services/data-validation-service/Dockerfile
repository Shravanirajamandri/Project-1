FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/data-validation-service-1.0.0.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
