#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build

#Information around who maintains the image
MAINTAINER mtopgul.com

# Add the application's jar to the container
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/api-gateway-0.0.1-SNAPSHOT.jar"]