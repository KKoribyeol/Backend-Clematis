FROM openjdk:12-alpine
COPY ./build/libs/*.jar kkoribyeol.jar
ENTRYPOINT ["java", "-Xmx100m", "-jar", "-Duser.timezone=Asia/Seoul", "/kkoribyeol.jar"]