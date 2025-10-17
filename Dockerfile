# Use a imagem oficial do OpenJDK
FROM openjdk:17-jdk-slim

# Copie o JAR gerado para o container
COPY target/projeto-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta (Render vai mapear a variável PORT)
EXPOSE 8080

# Comando para iniciar o app
ENTRYPOINT ["java","-jar","/app.jar"]