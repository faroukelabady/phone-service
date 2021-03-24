FROM adoptopenjdk:8-jre-hotspot
RUN mkdir /opt/app
COPY phoneService-0.0.1-SNAPSHOT.jar /opt/app/app.jar
COPY application.yml /opt/app/application.yml
COPY sample.db /opt/app/sample.db
COPY entry_point.sh entry_point.sh

