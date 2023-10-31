FROM openjdk:17

WORKDIR /app
COPY .env /app/.env
CMD source /app/.env
COPY target/buildYourDeck-0.0.1-SNAPSHOT.jar /app/buildYourDeck.jar

ENTRYPOINT ["java", "-jar", "buildYourDeck.jar"]