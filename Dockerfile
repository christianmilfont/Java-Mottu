# ==========================
# Etapa 1 - Build da aplicação
# ==========================
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

# Copia o código do projeto para dentro do container
COPY . .

# Dá permissão de execução ao Maven Wrapper
RUN chmod +x mvnw

# Executa o Maven Wrapper para gerar o JAR
RUN ./mvnw clean package -DskipTests

# ==========================
# Etapa 2 - Runtime
# ==========================
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
