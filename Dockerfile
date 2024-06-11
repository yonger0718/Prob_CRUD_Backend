FROM eclipse-temurin:17-jdk-alpine

# 設定工作目錄
WORKDIR /app

# 設定jar
COPY target/*.jar /app/app.jar

#設定配置環境
ENV SPRING_PROFILES_ACTIVE=prod

# 設定暴露端口
EXPOSE 8080

# 設定啟動方式
ENTRYPOINT ["java", "-jar", "app.jar"]