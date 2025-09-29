# Usando a imagem oficial do OpenJDK 21
FROM openjdk:21-jdk-slim

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o JAR gerado pelo Maven/Gradle para dentro do container
COPY target/Challange-Java-0.0.1-SNAPSHOT.jar myapp.jar

# Expõe a porta 8080, padrão para aplicações Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação Spring Boot
CMD ["java", "-jar", "myapp.jar"]
