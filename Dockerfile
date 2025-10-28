# =========================================================
# ETAPA 1: Construcción del proyecto con Maven
# =========================================================
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Define el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el pom.xml y el código fuente del microservicio
COPY ms-transport-tracking/pom.xml ms-transport-tracking/pom.xml
COPY ms-transport-tracking/src ms-transport-tracking/src

# Compila el proyecto y genera el JAR
RUN mvn -f ms-transport-tracking/pom.xml clean package -DskipTests


# =========================================================
# ETAPA 2: Imagen final con el JAR ejecutable
# =========================================================
FROM openjdk:17-jdk-slim

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia el .jar compilado desde la etapa anterior
COPY --from=build /app/ms-transport-tracking/target/*.jar app.jar

# Configura el puerto
ENV PORT=8080
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
