# Fase de build usando uma imagem base leve
FROM maven:3.8.5-openjdk-17-slim AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar apenas o pom.xml e baixar as dependências
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copiar o código fonte
COPY src ./src

# Fazer o build do projeto (sem testes para agilizar)
RUN mvn clean package -DskipTests

# Declarar argumento de versão
ARG VERSION

# Verificar se o JAR foi gerado corretamente
RUN ls /app/target/

# Fase final: imagem leve baseada em OpenJDK Alpine
FROM openjdk:17-alpine

# Definir diretório de trabalho
WORKDIR /app

# Instalar netcat sem cache para evitar arquivos desnecessários
RUN apk add --no-cache netcat-openbsd

# Copiar o arquivo JAR da fase de build
COPY --from=build /app/target/zup-${VERSION}.jar /app/zup-app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Comando de execução (aguardar MySQL antes de iniciar o app)
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app/zup-app.jar"]
