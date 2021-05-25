FROM openjdk:14-slim

WORKDIR /app
USER 1001

ARG MONGODB_PASSWORD

ENV JAVA_OPTS="-Dfile.encoding=UTF-8 -Xms64m -Xmx128m -XX:PermSize=64m -XX:MaxPermSize=128m -XX:+UseSerialGC -Djava.security.egd=file:/dev/./urandom"

COPY . .

EXPOSE 8080

CMD ["sh", "-c", "java ${JAVA_OPTS} -jar /app/target/petvaccine.jar"]