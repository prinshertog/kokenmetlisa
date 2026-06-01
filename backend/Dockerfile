FROM maven:amazoncorretto
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=0 /build/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]