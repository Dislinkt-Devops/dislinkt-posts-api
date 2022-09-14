FROM openjdk:11-jre-slim
COPY post-*.jar /usr/local/lib/post.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/post.jar"]