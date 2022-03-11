FROM openjdk:8-jdk-alpine
WORKDIR /usr/src/3am
#COPY . .
#CMD ["./mvnw", "package"]
ARG JAR_FILE_PATH=target/*.jar
COPY ${JAR_FILE_PATH} ./app.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "-Dspring.profiles.active=prod","./app.jar"]
