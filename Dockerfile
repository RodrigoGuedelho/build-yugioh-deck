FROM openjdk:17

WORKDIR /app

COPY target/buildYourDeck-0.0.1-SNAPSHOT.jar /app/buildYourDeck.jar

ENTRYPOINT ["java", "-jar", "buildYourDeck.jar"]