FROM openjdk:17
WORKDIR /app

COPY . .
EXPOSE 8080
ADD target/spring-boot-docker.jar spring-boot-docker.jar
CMD ["java", "-jar", "spring-boot-docker.jar"]