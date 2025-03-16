FROM maven:3.9.9-eclipse-temurin-21 AS builder

RUN apt-get update && apt-get install -y protobuf-compiler

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

# Manually set executable permission to protoc plugin
RUN chmod +x /root/.m2/repository/io/grpc/protoc-gen-grpc-java/1.68.1/protoc-gen-grpc-java-1.68.1-linux-aarch_64.exe || true

RUN mvn clean package

FROM openjdk:21-jdk AS runner

WORKDIR /app

COPY --from=builder ./app/target/billing-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4001
EXPOSE 9001

ENTRYPOINT ["java","-jar","app.jar"]