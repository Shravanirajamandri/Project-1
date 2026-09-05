FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/transaction-notification-service-1.0.0.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar","/app/app.jar"]
