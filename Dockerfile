# ==========================
# Etapa 1 - Build da aplicação
# ==========================
FROM eclipse-temurin:21-jdk-jammy AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia todo o conteúdo do projeto para dentro da imagem
COPY . .

# Executa o Maven Wrapper para compilar o projeto e gerar o .jar
RUN ./mvnw clean package -DskipTests

# ==========================
# Etapa 2 - Runtime
# ==========================
FROM eclipse-temurin:21-jdk-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
