FROM maven:3.8.5-openjdk-17


WORKDIR /app
COPY ./target/totalshake-0.0.1-SNAPSHOT.jar /app/total-shake-pedido-api.jar
COPY ./wait-for-it.sh /app/wait-for-it.sh

RUN chmod +x /app/wait-for-it.sh

ENTRYPOINT ["/bin/bash","/app/wait-for-it.sh", "db-pedido-api:3306","-s", "--", "java", "-jar", "/app/total-shake-pedido-api.jar","--DB_USERNAME=root", "--DB_PASSWORD=root", "--DB_URL=jdbc:mysql://banco_mysql:3306/totalShake"]