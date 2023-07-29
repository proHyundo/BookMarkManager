FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ \
"java", \
"-jar", \
"-Duser.timezone=Asia/Seoul", \
"app.jar" \
]