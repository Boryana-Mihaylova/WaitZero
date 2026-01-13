# 1) Build stage
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY .. .
RUN ./gradlew clean build -x test

# 2) Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]