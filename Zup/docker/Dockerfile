# Fase de build usando Maven
FROM maven:3.8.5-openjdk-17 AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos do projeto Maven
COPY pom.xml .
COPY src ./src

# Fazer o build do projeto
RUN mvn clean package -DskipTests

# Fase final: imagem com OpenJDK
FROM openjdk:17-jdk-slim

# Declarar argumento de versão
ARG VERSION

# Definir diretório de trabalho
WORKDIR /app

# Instalar netcat (necessário para o wait-for-it)
RUN apt-get update && apt-get install -y netcat

# Copiar o arquivo JAR da fase de build
COPY --from=build /app/target/zup-${VERSION}.jar /app/zup-app.jar

# Copiar o script wait-for-it
COPY docker/wait-for-it.sh /usr/local/bin/wait-for-it

# Dar permissão de execução ao script wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

# Expor a porta da aplicação
EXPOSE 8080

# Comando de execução (aguardar MySQL antes de iniciar o app)
ENTRYPOINT ["wait-for-it", "mysqldb:3306", "--", "java", "-Dspring.profiles.active=docker", "-jar", "/app/zup-app.jar"]
