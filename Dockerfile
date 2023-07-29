# App
FROM openjdk:11-jdk
WORKDIR /app

# Port
EXPOSE 9090

# 빌더 이미지에서 jar 파일만 복사
COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar

ENTRYPOINT [ \
"java", \
"-jar", \
"-Duser.timezone=Asia/Seoul", \
"app.jar" \
]