#先行編譯為jar file
FROM maven:3.9.7-eclipse-temurin-17-alpine AS build
# 將要編輯的模組設定為變數
ARG SERVICE_NAME
# 將工作目錄設為app
WORKDIR /app
# 將所有文件複製到工作目錄
COPY pom.xml .
COPY ${SERVICE_NAME}/pom.xml ${SERVICE_NAME}/
# 下载所有依赖项
COPY . .
#
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests -pl ${SERVICE_NAME} -am

# 開始執行
FROM eclipse-temurin:17-jre-jammy
# 將要編輯的模組設定為變數
ARG SERVICE_NAME
ARG PORT
# 設定JAR檔為參數
ARG JAR_FILE=/app/${SERVICE_NAME}/target/*.jar
# 把JAR COPY出來
COPY --from=build $JAR_FILE /app/runner.jar
#設定配置環境
ENV SPRING_PROFILES_ACTIVE=prod
# 設定暴露端口
EXPOSE ${PORT}
# 設定啟動方式
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]