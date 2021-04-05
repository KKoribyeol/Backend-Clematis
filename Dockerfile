#FROM openjdk:11-jre-slim
FROM openjdk:8-alpine
COPY ./build/libs/*.jar kkoribyeol.jar
ENTRYPOINT ["java", "-Xmx100m", "-jar", "-Duser.timezone=Asia/Seoul", "/kkoribyeol.jar"]