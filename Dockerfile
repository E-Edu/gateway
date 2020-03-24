FROM adoptopenjdk/openjdk8:alpine-jre

WORKDIR /eedu

COPY /build/libs/gateway-0.0.1-SNAPSHOT.jar gateway.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "gateway.jar"]
