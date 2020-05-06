FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /eedu

COPY /target/gateway-0.1-SNAPSHOT.jar gateway.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "gateway.jar"]
