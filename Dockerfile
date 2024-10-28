# Use a base image with JDK 21 installed
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build artifact (jar file) to the container
COPY target/crypto-recommendation-1.0.jar /app/crypto-recommendation-1.0.jar

# Copy the resources directory (csv files) to the container
COPY src/main/resources/ /app/resources/

# Expose the port your application will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "crypto-recommendation-1.0.jar"]
