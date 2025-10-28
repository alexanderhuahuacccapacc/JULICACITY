# Usa Java 17
FROM openjdk:17-jdk-slim

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR generado al contenedor
COPY # Copia el JAR generado
COPY ms-transport-tracking/target/ms-transport-tracking-0.0.1-SNAPSHOT.jar app.jar


# Expone el puerto que asignará Render
ENV PORT 8080
EXPOSE 8080

# Comando para arrancar la app
ENTRYPOINT ["java", "-jar", "app.jar"]

