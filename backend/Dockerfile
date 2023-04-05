FROM eclipse-temurin:17-jdk-focal
ENV APP_HOME=/app
WORKDIR $APP_HOME

COPY target/weather-api.jar app.jar
#EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
