FROM openjdk:11
RUN apt-get update

# TODO: install dependencies required for YOUR runtime
RUN apt-get install -y maven
COPY . /usr/app
WORKDIR /usr/app
RUN pwd && ls -lah

# TODO: change commands to build, run tests, and to start YOUR app
RUN mvn clean install package -Dmaven.test.skip=true -q -e

#RUN mvn package
CMD java -jar target/lottery-system-0.0.1-SNAPSHOT.jar