FROM openjdk:17
VOLUME /tmp
ADD ./target/person-0.0.1-SNAPSHOT.jar person.jar
ENTRYPOINT ["java","-jar","/person.jar"]