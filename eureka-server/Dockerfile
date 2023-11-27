FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
ADD pom.xml /app
RUN mvn verify --fail-never
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
EXPOSE 8761
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/
ENTRYPOINT ["java", "-jar", "eureka-server-0.0.1-SNAPSHOT.jar"]