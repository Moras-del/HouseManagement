FROM openjdk:11-jdk-slim
ADD target/housemanagement-0.0.1-SNAPSHOT.jar .
EXPOSE 8090
CMD java -jar housemanagement-0.0.1-SNAPSHOT.jar