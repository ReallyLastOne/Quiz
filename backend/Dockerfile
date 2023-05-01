FROM maven:3.8.4-openjdk-17 as build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests=true
RUN mvn --version

#
# Package stage
#
FROM tomcat:10-jdk17
COPY --from=build /home/app/target/quiz-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
RUN ["apt-get", "update"]
#RUN ["apt", "install", "nano", "-y"]

EXPOSE 8080
WORKDIR /usr/local/tomcat

RUN echo "OK"
RUN ["startup.sh"]