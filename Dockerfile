FROM openjdk:17-slim

# curl 설치 (헬스체크용)
RUN apt-get update && apt-get install -y curl

ARG JAR_FILE=build/libs/ouch.jar

COPY ${JAR_FILE} ouch.jar

ENTRYPOINT ["java", "-jar", "ouch.jar"]

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=5 \
  CMD status=$(curl -s -o /dev/null -w '%{http_code}' http://localhost:8080/actuator/health) && [ "$status" -eq 200 ]
