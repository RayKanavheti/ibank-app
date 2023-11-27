FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
EXPOSE 8089
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/
ENTRYPOINT ["java", "-jar", "account-service-0.0.1-SNAPSHOT.jar"]