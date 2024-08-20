FROM maven:3.8.5-openjdk-17-slim as BUILD

WORKDIR /src
COPY . /src
RUN mvn -DskipTests install -Dmaven.repo.local=.m2/repository package

FROM amazoncorretto:17
WORKDIR /app
COPY --from=BUILD src/target/citel-blood-donors-0.0.1-SNAPSHOT.jar  api-cetil-software.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-cetil-software.jar"]

