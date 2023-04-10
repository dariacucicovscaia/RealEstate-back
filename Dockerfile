FROM openjdk:11

ARG JAR_FILE=target/*.jar
COPY target/RealEstate-1.0-SNAPSHOT.jar app.jar

#TODO diference between entrypoint-can not be changed ;; cmd-can be overritten by the cli
CMD ["java", "-jar", "/app.jar"]