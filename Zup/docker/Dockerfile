# Fase de build usando Maven
FROM maven:3.9.7-eclipse-temurin-17-alpine  AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos do projeto Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar o código fonte
COPY src ./src

# Fazer o build do projeto
RUN mvn clean package -DskipTests

# Fase final: imagem com temurin
FROM eclipse-temurin:17-jre-alpine

# Declarar argumento de versão
ARG VERSION

# Definir diretório de trabalho
WORKDIR /app

# Instalar netcat (necessário para o wait-for-it)
RUN apt-get update && apt-get install -y netcat

# Copiar o arquivo JAR da fase de build
COPY --from=build /app/target/zup-${VERSION}.jar /app/zup-app.jar 

# Expor a porta da aplicação
EXPOSE 8080

# Comando de execução (aguardar MySQL antes de iniciar o app)
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app/zup-app.jar"]
