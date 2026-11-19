FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src src
RUN mvn -q clean package

FROM payara/server-web:6.2025.11-jdk17
COPY --chown=payara:payara docker/domain.xml /opt/payara/appserver/glassfish/domains/domain1/config/domain.xml
COPY --chown=payara:payara docker/init_0_aiven_ssl.sh /opt/payara/scripts/init_0_aiven_ssl.sh
COPY --from=build /app/target/SistemaBiblioteca.war /opt/payara/deployments/SistemaBiblioteca.war
