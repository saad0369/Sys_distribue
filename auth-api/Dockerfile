FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

COPY src src
COPY pom.xml .

RUN mvn clean package -DskipTests

EXPOSE 8005

ENTRYPOINT ["java", "-jar", "target/auth-api-0.0.1-SNAPSHOT.jar"]