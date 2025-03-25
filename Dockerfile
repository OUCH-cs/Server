FROM openjdk:17

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} ouch.jar

ARG ENV_KEY
ENV APP_ENV_KEY=${ENV_KEY}

ENTRYPOINT ["java", "-jar", "ouch.jar", "--spring.profiles.active=prod", "--jasypt.encryptor.password=${APP_ENV_KEY}"]
