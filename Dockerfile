FROM openjdk:8-jdk-alpine
MAINTAINER cagri.altug
COPY build/libs/com.altugcagri.smep-0.0.1-SNAPSHOT.jar /usr/app/smep.jar
EXPOSE 8080
WORKDIR /usr/app/
CMD ["java","-jar","-Dspring.profiles.active=prod","smep.jar"]
