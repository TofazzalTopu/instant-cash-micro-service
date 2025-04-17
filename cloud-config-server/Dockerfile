FROM openjdk:11
ADD target/cloud-config-server.jar cloud-config-server.jar
ENTRYPOINT ["java", "-jar", "cloud-config-server.jar"]
EXPOSE 9092