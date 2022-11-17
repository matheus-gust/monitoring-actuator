FROM adoptopenjdk/openjdk11:alpine

LABEL version="0.0.1" description="Monitoring Actuator"

COPY ./target/estudoactuator-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8281

ENTRYPOINT exec java -Dspring.datasource.url=jdbc:postgresql://postgres:5432/monitoring-actuator -jar app.jar