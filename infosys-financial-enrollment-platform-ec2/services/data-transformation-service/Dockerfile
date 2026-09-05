FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/data-transformation-service-1.0.0.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","/app/app.jar"]
