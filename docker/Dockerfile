ARG JDK_IMAGE
FROM ${JDK_IMAGE}
MAINTAINER Galaxy
VOLUME /tmp
WORKDIR /home/tsg/galaxy/cn-object-scheduler
COPY config config
ARG JAR_FILE
COPY ${JAR_FILE} cn-object-scheduler.jar
#dockercompose set JAVA_OPTS
ENV JAVA_OPTS=" -Xms1024m -Xmx2048m "
ENV LANG=en_US.UTF-8 LANGUAGE=en_US:en LC_ALL=en_US.UTF-8
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar cn-object-scheduler.jar" ]
