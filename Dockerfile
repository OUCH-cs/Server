FROM openjdk:17-slim

# curl 설치 (헬스체크용) openjdk:17 (slim버전이 아닌 full 버전)로 설치하고 RUN줄 삭제 해도 됨
RUN apt-get update && apt-get install -y curl

ARG JAR_FILE=build/libs/ouch.jar

COPY ${JAR_FILE} ouch.jar

ENTRYPOINT ["java", "-jar", "ouch.jar"]

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=5 \
  CMD status=$(curl -s -o /dev/null -w '%{http_code}' http://localhost:8080/actuator/health) && [ "$status" -eq 200 ]
