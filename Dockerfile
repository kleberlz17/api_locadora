FROM openjdk:21-jdk-slim
WORKDIR /app
COPY app.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]
