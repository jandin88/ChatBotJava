FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B
COPY src src
RUN ./mvnw clean package -DskipTests -B
EXPOSE 8080
CMD ["java","-jar","target/chatbotjava-0.0.1-SNAPSHOT.jar"]
