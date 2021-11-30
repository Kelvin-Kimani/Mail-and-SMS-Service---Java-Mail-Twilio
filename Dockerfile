FROM openjdk:11
ADD target/scheduler.jar scheduler.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "scheduler.jar"]

