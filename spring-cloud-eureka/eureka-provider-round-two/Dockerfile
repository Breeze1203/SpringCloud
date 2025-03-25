FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 2222
ENTRYPOINT ["java", "-jar", "app.jar"]
