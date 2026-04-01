# 使用 JDK 17 的轻量级版本作为基础镜像
#FROM openjdk:17-jdk-slim
# 或者方案 B（如果你坚持用 Temurin）：
FROM eclipse-temurin:17-jre

# 设置镜像内部的工作目录
WORKDIR /app

# 将 target 目录下的 jar 包拷贝到镜像里的 app.jar
COPY target/product-service.jar app.jar

# 容器启动时执行的命令
ENTRYPOINT ["java", "-jar", "app.jar"]