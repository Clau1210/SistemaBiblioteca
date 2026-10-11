FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src src
RUN mvn -q clean package

FROM payara/server-full:6.2025.11-jdk17
COPY --from=build /app/target/SistemaBiblioteca.war /opt/payara/deployments/SistemaBiblioteca.war
