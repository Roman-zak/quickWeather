FROM azul/zulu-openjdk-alpine:17-jre

ARG USERNAME=weatheruser
ARG USER_UID=1000
ARG USER_GID=$USER_UID

RUN addgroup -g $USER_GID -S $USERNAME && adduser -u $USER_UID -G $USERNAME -S $USERNAME

ENV APP_HOME=/app
ENV SNKEYAML_VERSION=2.0

WORKDIR $APP_HOME

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

USER ${USERNAME}
CMD ["java", "-jar", "app.jar"]
#TODO check docker vulnerabilities
#TODO secure and small image