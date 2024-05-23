FROM openjdk:17-jdk
COPY target/*.jar application.jar
ENTRYPOINT ["java", "-jar","application.jar"]