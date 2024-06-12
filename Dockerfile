#先行編譯為jar file
FROM maven:3.9.7-eclipse-temurin-17-alpine AS build
# 將工作目錄設為app
WORKDIR /app
# 將所有文件複製到工作目錄
COPY . .
#
RUN --mount=type=cache,target=/root/.m2 mvn clean package -Dmaven.test.skip

# 開始執行
FROM eclipse-temurin:17-jre-jammy
# 設定JAR檔為參數
ARG JAR_FILE=/app/target/*.jar
# 把JAR COPY出來
COPY --from=build $JAR_FILE /app/runner.jar
#設定配置環境
ENV SPRING_PROFILES_ACTIVE=prod
# 設定暴露端口
EXPOSE 8080
# 設定啟動方式
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]