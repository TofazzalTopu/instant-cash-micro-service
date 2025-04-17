FROM openjdk:11-jdk-alpine
ADD target/instant-cash-api.jar instant-cash-api.jar
ENTRYPOINT ["java", "-jar", "instant-cash-api.jar"]
EXPOSE 8081