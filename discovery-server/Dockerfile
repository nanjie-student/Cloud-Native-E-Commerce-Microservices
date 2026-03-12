FROM eclipse-temurin:17-jre
WORKDIR /app
# 使用通配符，只要是 target 下的 jar 包都行
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]