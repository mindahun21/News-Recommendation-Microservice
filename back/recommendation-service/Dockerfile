
FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /build

COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

RUN apk add --no-cache libgcc libstdc++
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=builder /build/target/*.jar app.jar

RUN chown spring:spring app.jar

USER spring:spring

ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", \
    "app.jar"]
