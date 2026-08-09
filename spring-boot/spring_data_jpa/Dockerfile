FROM eclipse-temurin:25-jdk-alpine

WORKDIR /spring-boot/spring_data_jpa

COPY target/*.jar /spring-boot/spring_data_jpa/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/spring-boot/spring_data_jpa/app.jar"]