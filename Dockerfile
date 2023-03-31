FROM openjdk:11

ARG JAR_FILE=target/*.jar
COPY target/RealEstate-1.0-SNAPSHOT.jar app.jar

#TODO diference between entrypoint and cmd
ENTRYPOINT ["java", "-jar", "/app.jar"]