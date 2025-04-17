FROM openjdk:11
ADD target/user-service.jar user-service.jar
ENTRYPOINT ["java", "-jar", "user-service.jar"]
EXPOSE 8081