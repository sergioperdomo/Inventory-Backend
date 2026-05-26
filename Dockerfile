# ─── Etapa 1: Compilar el proyecto ───────────────────────────────────────────
# Usamos una imagen con Maven y Java 21 para compilar
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos primero solo el pom.xml para aprovechar el cache de Docker
# Si el pom.xml no cambia, Docker no re-descarga las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Ahora copiamos el código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# ─── Etapa 2: Imagen final liviana ───────────────────────────────────────────
# Solo necesitamos Java para correr el .jar, no Maven
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiamos el .jar generado en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Puerto que expone el contenedor
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]