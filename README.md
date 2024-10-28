# Crypto Price Recommendation Service

## Overview
This is a web service developed using the Spring framework, Java 21, and Maven as a project management tool.
The service provides endpoints to manage cryptocurrency prices and recommendations.

## Technologies
- **Java Version**: 21
- **Framework**: Spring
- **Build Tool**: Maven

## API Documentation

### Base URL
- http://localhost:8080/api/

### Endpoints

1. **Get All Crypto Prices**
    - **Endpoint**: `/prices`
    - **Method**: `GET`
    - **Description**: Retrieves all current cryptocurrency prices.
    - **Response**:
        - **200 OK**: A list of current prices for all cryptocurrencies.
        - **Example Response**:
          ```json
          [123.45, 678.90]
          ```

2. **Get All Statistics Per Month**
    - **Endpoint**: `/statistics`
    - **Method**: `GET`
    - **Description**: Retrieves statistics for each cryptocurrency per month.
    - **Response**:
        - **200 OK**: A list of statistics for each cryptocurrency.
        - **Example Response**:
          ```json
          [
            {
              "symbol": "BTC",
              "oldest": {},
              "newest": {},
              "lowestPrice": {},
              "highestPrice": {}
            }
          ]
          ```

3. **Get Statistics for Specific Crypto**
    - **Endpoint**: `/statistics/{cryptoSymbol}`
    - **Method**: `GET`
    - **Path Parameter**:
        - `cryptoSymbol`: The symbol of the cryptocurrency (e.g., `BTC`, `ETH`).
    - **Description**: Retrieves monthly statistics for a specific cryptocurrency.
    - **Response**:
        - **200 OK**: Statistics for the specified cryptocurrency.
        - **Example Response**:
          ```json
          {
            "symbol": "BTC",
            "oldest": {},
            "newest": {},
            "lowestPrice": {},
            "highestPrice": {}
          }
          ```

4. **Get Normalized Ranges by Frequency**
    - **Endpoint**: `/recommendations/by-normalized-range/{frequency}`
    - **Method**: `GET`
    - **Path Parameter**:
        - `frequency`: The frequency to filter by (e.g., `PER_DAY`, `PER_MONTH`).
    - **Query Parameters**:
        - `order` (optional): Specify the order of results (ascending or descending).
        - `searchedDay` (optional): A specific date to filter results.
    - **Description**: Retrieves cryptocurrencies by normalized price range based on frequency.
    - **Response**:
        - **200 OK**: A list of cryptocurrencies with their normalized ranges.
        - **Example Response**:
          ```json
          [
            {
              "symbol": "BTC",
              "normalizedRange": 0.15
            }
          ]
          ```

5. **Get Highest Normalized Range**
    - **Endpoint**: `/recommendations/highest-normalized-range/{frequency}`
    - **Method**: `GET`
    - **Path Parameter**:
        - `frequency`: The frequency to filter by.
    - **Query Parameter**:
        - `searchedDay`: The date for which to retrieve the highest normalized range.
    - **Description**: Retrieves the cryptocurrency with the highest normalized price range for the specified frequency.
    - **Response**:
        - **200 OK**: The cryptocurrency with the highest normalized range.
        - **Example Response**:
          ```json
          {
            "symbol": "BTC",
            "normalizedRange": 0.25
          }
          ```

6. **Get Recommendations by Crypto Symbol**
    - **Endpoint**: `/recommendations/{cryptoSymbol}`
    - **Method**: `GET`
    - **Path Parameter**:
        - `cryptoSymbol`: The symbol of the cryptocurrency (e.g., `BTC`).
    - **Description**: Retrieves recommendations for a specific cryptocurrency by its symbol.
    - **Response**:
        - **200 OK**: A list of recommendations for the specified cryptocurrency.
        - **Example Response**:
          ```json
          [
            {
              "price": 123.45,
              "date": "2024-10-01"
            }
          ]
          ```

### Running the Service in Docker

Build the Docker Image
Ensure you have Docker installed and running.

1. Navigate to the directory containing the Dockerfile.
   - cd crypto-recommendation

2. Build the Docker image:
   - docker build -t crypto-recommendation .

3. Run the Docker Container
   - docker run -p 8080:8080 crypto-recommendation
   This command maps port 8080 of the container to port 8080 on your host machine.

4. Accessing the Service
   - After starting the application (either locally or in Docker), you can access it at: http://localhost:8080

Base Image: Uses a slim version of OpenJDK 21 for a lightweight container.
Working Directory: Sets /app as the working directory in the container.
Copy Commands: Copies the built JAR file and resources into the container.
Port Exposure: Exposes port 8080 for the application.
Entry Point: Specifies the command to run the application.


## Conclusion
This documentation provides an overview of the API and the underlying service logic for handling cryptocurrency price data and recommendations. For further details on each method, please refer to the source code.

---
