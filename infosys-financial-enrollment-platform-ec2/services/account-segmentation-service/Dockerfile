FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/account-segmentation-service-1.0.0.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","/app/app.jar"]
