FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8081

ENV HUGGINGFACE_API_KEY=${HUGGINGFACE_API_KEY}

ENTRYPOINT ["java", "-jar", "app.jar"]
