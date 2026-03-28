FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/config-server-*.jar app.jar
# 确保这里匹配你 target 里的 jar 包名字
COPY target/product-service-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]