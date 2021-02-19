FROM maven:3.6.3-jdk-8 AS build-env
WORKDIR /usr/app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY . ./
RUN mvn package

FROM openjdk:8-jre-alpine as run-env
EXPOSE 8088
WORKDIR /usr/app

COPY --from=build-env /usr/app/target/s4n-drones-1.0-SNAPSHOT.jar ./s4n-drones-1.0-SNAPSHOT.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "s4n-drones-1.0-SNAPSHOT.jar"]