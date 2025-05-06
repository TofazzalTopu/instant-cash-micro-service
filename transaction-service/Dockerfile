# ---- Build Stage ----
FROM maven:3.8.5-openjdk-11 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

# ---- Runtime Stage (Distroless) ----
FROM gcr.io/distroless/java11-debian11

# Copy the built JAR into the root (or another appropriate path)
COPY --from=build /build/target/transaction-service.jar /app/transaction-service.jar

# Expose port for documentation (has no functional effect in distroless)
EXPOSE 8088

# Use the most minimal ENTRYPOINT for distroless
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/transaction-service.jar"]
