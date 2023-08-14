FROM openjdk:17
LABEL maintainer="Irfan.Suwadi"
ADD target/rest-0.0.1-SNAPSHOT.jar springboot-book-app.jar
ENTRYPOINT ["java","-jar","springboot-book-app.jar"]
