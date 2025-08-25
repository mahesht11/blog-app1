FROM openjdk:17

ARG JAR_FILE=targer/*.jar

COPY ${JAR_FILE} blog-app.jar

ENTRYPOINT ["java", "-jar", "/blog-app.jar"]

EXPOSE 8081
