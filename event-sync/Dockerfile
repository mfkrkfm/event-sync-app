FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY . .

RUN ./mvnw -B -DskipTests package

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
