FROM openjdk:17
EXPOSE 80
ADD target/authentification-0.0.1-SNAPSHOT.jar authentification-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/authentification-0.0.1-SNAPSHOT.jar"]