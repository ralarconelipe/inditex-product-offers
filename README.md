# Product Offers

This project is a Spring Boot application designed to manage product offers. It provides functionality to create,
retrieve, and delete offers, as well as flatten overlapping date ranges by applying the price with the highest priority.

__Table of content__

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Hexagonal Architecture Model](#hexagonal-architecture-model)
    - [Key Components](#key-components)
    - [Benefits](#benefits)
- [Getting Started](#getting-started)
    - [Clone the Repository](#clone-the-repository)
    - [Build the Project](#build-the-project)
    - [Run the Application](#run-the-application)
- [API Endpoints](#api-endpoints)
    - [Offer Management](#offer-management)
- [Database Schema](#database-schema)
    - [Sample Data](#sample-data)
- [How to Flatten Date Ranges](#how-to-flatten-date-ranges)
    - [Example Input](#example-input)
    - [Example Output](#example-output)
- [Code Coverage with JaCoCo](#code-coverage-with-jacoco)
    - [Generating the Coverage Report](#generating-the-coverage-report)
    - [Viewing the Report](#viewing-the-report)

## Features

- **Create Offers**: Add new offers with details such as brand, start and end dates, price, priority, and currency.
- **Retrieve Offers**: Fetch all offers, a specific offer by ID, or offers filtered by brand and part number.
- **Flatten Date Ranges**: Resolve overlapping date ranges by applying the price with the highest priority.
- **Delete Offers**: Remove all offers or a specific offer by ID.

## Technologies Used

- **Java**: Core programming language.
- **Spring Boot**: Framework for building the application.
- **H2 Database**: In-memory database for development and testing.
- **Maven**: Build and dependency management tool.
- **Jakarta Validation**: For request validation.
- **Lombok**: To reduce boilerplate code.
- **Logback**: Logging framework for managing application logs.
- **OpenAPI/Swagger**: For API documentation and testing.
- **JaCoCo**: Java Code Coverage library for measuring test coverage.
- **JUnit 5**: Testing framework for writing unit and integration tests.
- **Hexagonal Architecture**: Architectural pattern for clean separation of concerns.

## Prerequisites

- Java 17 or higher
- Maven 3.8 or higher

## Hexagonal Architecture Model

The project follows the **Hexagonal Architecture** (also known as **Ports and Adapters Architecture**) to ensure a clean
separation of concerns and make the application more maintainable, testable, and adaptable to changes.

### Key Components

1. **Domain module (Core)**:
    - Contains the business logic and domain models.
    - Independent of any external frameworks or technologies.
    - Example: `Offer` model and business rules.

2. **Application module**:
    - Coordinates the application logic and interacts with the domain layer.
    - Defines use cases and services.
    - Example: Services that handle offer creation, retrieval, and flattening.

3. **Infrastructure module**:
    - Bridges between the application and external systems (e.g., databases, APIs, or user interfaces).
    - Divided into:
        - **Inbound Adapters**: Handle input from external sources (e.g., REST controllers).
        - **Outbound Adapters**: Handle output to external systems (e.g., repositories, external APIs).
    - Example: `OfferController` (inbound) and `OfferRepository` (outbound).

4. **Bootloader module**:
    - Contains the main application entry point to start the Spring Boot application.

5. **Integration Test module**:
    - Contains integration tests for the application.

### Benefits

- **Testability**: The core logic can be tested independently of external systems.
- **Flexibility**: Adapters can be swapped without affecting the core logic.
- **Maintainability**: Clear separation of concerns simplifies code management.

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/xxxx/inditex-product-offers.git
cd inditex-product-offers
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

Test on the browser via OpenAPI in local
----------------------------------------

```sh
http://localhost:8080/v3/api-docs

http://localhost:8080/swagger-ui/index.html
```

## API Endpoints

### Offer Management

- **Create Offer**  
  `POST /offer`  
  Request Body:
  ```json
  {
    "brandId": 1,
    "startDate": "2020-06-14T00.00.00Z",
    "endDate": "2020-12-31T23.59.59Z",
    "priceList": 1,
    "partNumber": "0001002",
    "priority": 0,
    "price": 35.50,
    "currency": "EUR"
  }
  ```

- **Get All Offers**  
  `GET /offer`

- **Get Offer by ID**  
  `GET /offer/{offerId}`

- **Get Offers by Brand and Part Number**  
  `GET /brand/{brandId}/partnumber/{partNumber}/offer`

- **Delete All Offers**  
  `DELETE /offer`

- **Delete Offer by ID**  
  `DELETE /offer/{offerId}`

## Database Schema

The application uses the following schema for the `OFFER` table:

```sql
CREATE TABLE BRAND (ID int not null, BRAND_NAME varchar(25), primary key (ID));
CREATE TABLE OFFER (ID bigint not null, BRAND_ID int not null, START_DATE timestamp with time zone, END_DATE timestamp with time zone, PRICE_LIST bigint, PART_NUMBER varchar(7), PRIORITY integer, PRICE decimal(4,2), CURR varchar(3), primary key (ID));

ALTER TABLE OFFER ADD FOREIGN KEY (BRAND_ID) REFERENCES BRAND(ID);

INSERT INTO BRAND (ID, BRAND_NAME) VALUES (1, 'HIBERUS');
```

### Sample Data

```sql
INSERT INTO BRAND (ID, BRAND_NAME) VALUES (1, 'HIBERUS');

INSERT INTO OFFER (ID, BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PART_NUMBER, PRIORITY, PRICE, CURR) VALUES (1, 1, '2020-06-14T00.00.00Z', '2020-12-31T23.59.59Z', 1, '0001002', 0, 35.50, 'EUR');
```

## How to Flatten Date Ranges

The `OfferDateRangeFlattener` utility resolves overlapping date ranges by applying the price with the highest priority. It
generates non-overlapping intervals with the correct price applied.

### Example Input

```java
List<Offer> offers = List.of(
  new Offer(1L, 1, ZonedDateTime.parse("2020-06-14T00.00.00Z"), ZonedDateTime.parse("2020-12-31T23.59.59Z"), 1, "0001002", 0,
    BigDecimal.valueOf(35.50), "EUR"),
  new Offer(2L, 1, ZonedDateTime.parse("2020-06-14T15.00.00Z"), ZonedDateTime.parse("2020-06-14T18.30.00Z"), 2, "0001002", 1,
    BigDecimal.valueOf(25.45), "EUR"),
  new Offer(3L, 1, ZonedDateTime.parse("2020-06-15T00.00.00Z"), ZonedDateTime.parse("2020-06-15T11.00.00Z"), 3, "0001002", 1,
    BigDecimal.valueOf(30.50), "EUR"),
  new Offer(4L, 1, ZonedDateTime.parse("2020-06-15T16.00.00Z"), ZonedDateTime.parse("2020-12-31T23.59.59Z"), 4, "0001002", 1,
    BigDecimal.valueOf(38.95), "EUR")
);
List<OfferDateRangeFlattenedResponseDTO> flattenedOffers = OfferDateRangeFlattener.flatten(offers);
```

### Example Output

```
From “2020-06-14T00.00.00Z” – until “2020-06-14T14.59.59Z” price: 35.50
From “2020-06-14T15.00.00Z” – until “2020-06-14T18.29.59Z” price: 25.45
From “2020-06-14T18.30.00Z” – until “2020-06-14T23.59.59Z” price: 35.50
From “2020-06-15T00.00.00Z” – until “2020-06-15T10.59.59Z” price: 30.50
From “2020-06-15T11.00.00Z” – until “2020-06-15T15.59.59Z” price: 35.50
From “2020-06-15T16.00.00Z” – until “2020-12-31T23.59.59Z” price: 38.95
```

## Code Coverage with JaCoCo

This project uses **JaCoCo** (Java Code Coverage) to measure test coverage. JaCoCo generates detailed reports that help
identify untested parts of the codebase.

### Generating the Coverage Report

To generate the JaCoCo coverage report, run the following Maven command:

```bash
mvn clean verify
```

After the command completes, the coverage report will be available at:

```bash
target/site/jacoco/index.html
```

### Viewing the Report

Open the ``index.html`` file in a web browser.
Navigate through the report to view coverage details for packages, classes, and methods.

#### Example Coverage Metrics

The report provides metrics such as:

- Instruction Coverage: Percentage of executed bytecode instructions.
- Branch Coverage: Percentage of executed branches in control structures (e.g., if, switch).
- Line Coverage: Percentage of executed lines of code.
- Method Coverage: Percentage of executed methods.
- Class Coverage: Percentage of executed classes.

Use this report to ensure high test coverage and identify areas for improvement.