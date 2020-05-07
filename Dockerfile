FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /eedu

COPY gateway-core/target/gateway-core-0.1-SNAPSHOT.jar gateway.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "gateway.jar"]
