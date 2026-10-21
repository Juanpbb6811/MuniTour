# Etapa 1: Compilación del proyecto con Maven y Java 21
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copiar el pom.xml y descargar dependencias para aprovechar la caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y empaquetar el JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera de ejecución con JRE 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el artefacto compilado desde la etapa de build
COPY --from=build /app/target/munitour-backend-0.0.1-SNAPSHOT.jar app.jar

# Puerto donde corre Spring Boot
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
