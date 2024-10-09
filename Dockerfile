#FROM eclipse-temurin:17-jdk
#COPY ./build/libs/*SNAPSHOT.jar project.jar
#ENTRYPOINT ["java", "-jar", "project.jar"]

# 빌드에 사용할 Eclipse Temurin OpenJDK 17 이미지
FROM eclipse-temurin:17-jdk as build

# 작업 디렉토리 설정
WORKDIR /workspace/app

# gradlew 및 소스 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# 빌드 수행 (테스트는 생략)
RUN ./gradlew build -x test

# 최종 이미지를 위한 단계
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# 빌드 단계에서 나온 jar 파일을 복사
COPY --from=build /workspace/app/build/libs/*.jar app.jar

# 포트 8080 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
