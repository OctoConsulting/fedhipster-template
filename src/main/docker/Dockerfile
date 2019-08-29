FROM openjdk:11-jre-slim-stretch

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS=""

# Add a jhipster user to run our application so that it doesn't need to run as root
RUN adduser --disabled-password --shell /bin/sh jhipster
WORKDIR /home/jhipster

ADD chromedriver chromedriver
ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown jhipster:jhipster entrypoint.sh

RUN apt-get update && apt-get -y install libglib2.0-dev libxi6 libnss3-dev wget
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN dpkg -i google-chrome-stable_current_amd64.deb; apt-get -fy install

USER jhipster

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 8080

ADD *.jar app.jar

CMD java -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -jar app.jar
