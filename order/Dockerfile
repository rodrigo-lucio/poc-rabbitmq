FROM openjdk:17
VOLUME /tmp
ADD ./target/order-0.0.1-SNAPSHOT.jar order.jar
ENTRYPOINT ["java","-jar","/order.jar"]