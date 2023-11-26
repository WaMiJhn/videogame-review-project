FROM gradle:7.6.0-jdk17
WORKDIR /opt/app
COPY ./build/libs/individual_project-1.0-SNAPSHOT.jar ./

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar individual_project-1.0-SNAPSHOT.jar"]

