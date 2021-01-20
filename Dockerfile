FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
COPY target/*.jar molspot-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/molspot-0.0.1-SNAPSHOT.jar"]