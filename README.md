# Price Comparator - Market
### Korpos Botond

The goal of this project was to build the backend for a "Price Comparator - Market" application. The system allows users to track price changes, find the best deals, and manage their shopping lists effectively.

Tech Stack
Build tool: Gradle
Framework: Spring Boot
Database: MySQL

### Project Structure Overview

#### Main Package
`com.diboti.pricecomparatormarket`

#### 1. controller
Contains REST controllers handling incoming HTTP requests.
- `DiscountController`
- `ProductController`
- `exceptions` – Custom exceptions related to controllers.

#### 2. dto
Handles data transfer objects.
- `incoming` – DTOs for incoming requests.
- `outgoing` – DTOs for outgoing responses.

#### 3. mapper
Contains classes to map between models and DTOs.

#### 4. model
Holds the core domain entities/models.

#### 5. repo
Contains Data Access Object (DAO) interfaces for database interaction.
- `DiscountDao`
- `ProductAlertDao`
- `ProductDao`
- `exceptions` – Custom exceptions for the repository layer.

#### 6. service
Contains business logic.
- `impl` – Implementations of the service interfaces.
  - `DiscountService`
  - `ProductService`
- `exceptions` – Custom exceptions for the service layer.

#### 7. PriceComparatorMarketApplication
The main Spring Boot application class.

#### resources
- `application.properties` – Configuration file for Spring Boot.

### Build and Run Instructions

#### Prerequisites
Ensure the following are installed on your machine:
- [Java 21](https://jdk.java.net/21/)
- [Gradle 8.14+](https://gradle.org/releases/)
- [Git](https://git-scm.com/)
- [MySQL 8.0+](https://www.mysql.com/downloads/)

Modify src/main/resources/application.properties to configure database settings and server port.

#### Clone the Repository
```bash
git clone https://github.com/utpeti/price_comparator_market.git
cd price_comparator_market
./gradlew clean build
./gradlew bootRun
```

#### Python script to convert CSV files into database tables
This script requires Python3 to run and mysql, pandas packages.
```bash
git clone https://github.com/utpeti/csv_converter.git
cd csv_converter
python3 converter.py
```


### Simplification
The alert system is sending the alerts to the console (System.out.println()).


### How to Use the Implemented Features

#### `/api/v1/products`

- **GET** `/api/v1/products/{id}/alternatives`:  
  Returns alternative products for the given product ID in a standard measurement format.

- **GET** `/api/v1/products/{id}` with query params (`store`, `brand`, or `product_category`):  
  Retrieves price history and discounts for a product based on the selected filter.

- **POST** `/api/v1/products/notify/{id}`:  
  Sets a price alert for a product based on user email and desired price.

- **DELETE** `/api/v1/products/notify/{id}`:  
  Deletes a previously set price alert for a product.

- **POST** `/api/v1/products/optimize`:  
  Optimizes a shopping cart by selecting the cheapest stores for the items (id) given.

---

#### `/api/v1/discounts`

- **GET** `/api/v1/discounts/best`:  
  Retrieves products with the highest discounts currently available.

- **GET** `/api/v1/discounts/latest`:  
  Returns the most recently added or updated discounts.
