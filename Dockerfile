FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /opt/app

COPY workouttracker/.mvn/ .mvn
COPY workouttracker/mvnw workouttracker/pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY workouttracker/src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=prod -jar /opt/app/app.jar"]
