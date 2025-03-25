FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java","-Xms64m", "-Xmx256m", "-Xss256k", "-XX:MaxMetaspaceSize=64m", "-XX:MaxDirectMemorySize=16m", "-jar", "app.jar"]

