
# E-Commerce Application

**GitHub Repository:** ` (https://github.com/Shree-Pai/E-Commerce)`


## Description

This is a **Spring Boot–based E-Commerce system** that enables **product management, user management, order handling, and payment integration**. It also includes **discount features, review systems, transaction logging, and robust error handling**. The system is tested with **JUnit (unit & integration tests)** for reliability.

-----

## Features

  - **Product Management**

      - CRUD operations for products
      - Category-based organization
      - Discount application on products

  - **Cart & Order Processing**

      - Add/update/remove items in cart
      - Place orders & track order status
      - Support for multiple order states (Pending, Shipped, Delivered)

  - **User Management**

      - User registration & authentication (JWT)
      - Role-based access (Admin/User)
      - Address management

  - **Payment Integration**

      - Secure payment processing
      - Payment status tracking
      - Multiple payment methods supported

  - **Reviews & Ratings**

      - Add and manage product reviews
      - Star ratings and feedback system

  - **Logging & Error Handling**

      - Logs for user transactions & admin actions
      - Global exception handling for predictable error responses

  - **Testing**

      - Unit tests for services
      - Integration tests for controllers
      - Test reports available under `target/surefire-reports/`

-----

## Tech Stack

  - **Backend Framework**: Spring Boot 3.5.4
  - **Security**: Spring Security + JWT
  - **Database**: MySQL
  - **API Docs**: Springdoc OpenAPI (Swagger UI)
  - **Validation**: Hibernate Validator
  - **Build Tool**: Maven
  - **Testing**: JUnit 5 + Mockito
  - **Logging**: SLF4J + Logback

-----

## Getting Started

### Prerequisites

  - Java 17+
  - Maven 3+
  - MySQL 8+

### Setup Instructions

1.  **Clone the repository**
    ```bash
    git clone https://github.com/yourusername/ECommerceApplication.git
    cd ECommerceApplication
    ```
2.  **Configure Database in `src/main/resources/application.properties`**:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```
3.  **Build & Run**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
4.  **Access the Application**
      - API Base URL → `http://localhost:8080/api`.
      - Swagger UI → `http://localhost:8080/swagger-ui/index.html`

### API Documentation

  - OpenAPI Spec available at:
    ```bash
    src/main/resources/static/openapi.yaml
    ```
  - Swagger UI (auto-generated docs):
    ```bash
    http://localhost:8080/swagger-ui.html
    ```

### Testing

Run all tests:

```bash
mvn test
```

  - Unit Tests → Services (e.g., UserServiceTest, ProductServiceTest)
  - Integration Tests → Controllers (e.g., OrderControllerIntegrationTest, AuthControllerIntegrationTest)

### Default Admin User

An Admin user is pre-seeded when the app starts.

```
Email: admin@admin.com
Password: admin123
```

(Admin credentials configurable in `AdminSeeder.java`)

-----

## Project Structure

```
com.scem.ecommerce
 ├── ECommerceApplication.java       # Entry point
 ├── controller                      # REST controllers
 ├── service                         # Business logic
 ├── dao                             # JPA repositories
 ├── entity                          # Database models
 ├── dto                             # Request/response objects
 ├── security                        # JWT & security config
 ├── config                          # App-level configs
 ├── util                            # Utility classes
 └── exception                       # Custom exceptions & handlers
```

-----

# E-commerce API Endpoints

This section provides a comprehensive overview of the key API endpoints for the E-commerce application. The endpoints are categorized by their functionality and clearly indicate whether a JWT token is required for access and which user roles have permission.

-----

###  Authentication APIs (No token required)

#### 1\. Register User

Creates a new user account in the system.

  - **Method:** `POST`

  - **Endpoint:** `/api/auth/register`

  - **Request Body:**

    ```json
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "password": "securepassword123",
      "mobileNumber": "9876543210"
    }
    ```

  - **Responses:**

      - `201 Created`: User registered successfully.
        ```json
        {
          "id": 123,
          "firstName": "John",
          "lastName": "Doe",
          "email": "john.doe@example.com",
          "mobileNumber": "9876543210",
          "roles": ["ROLE_USER"]
        }
        ```
      - `409 Conflict`: Email already exists.

#### 2\. User Login

Authenticates a regular user and returns a JWT token with `ROLE_USER`. This token should be used for subsequent API calls requiring user-level authentication.

  - **Method:** `POST`

  - **Endpoint:** `/api/auth/login`

  - **Request Body:**

    ```json
    {
      "email": "john.doe@example.com",
      "password": "securepassword123"
    }
    ```

  - **Response (`200 OK`):**

    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImlhd...NzE2MjM5Miwicm9sZXMiOlsiUk9MRV9VU0VSIl19.signature",
      "email": "john.doe@example.com",
      "roles": ["ROLE_USER"]
    }
    ```

#### 3\. Admin Login

Authenticates an administrator and returns a JWT token with `ROLE_ADMIN`. This token grants access to administrative endpoints.

  - **Method:** `POST`

  - **Endpoint:** `/api/auth/login`

  - **Request Body:**

    ```json
    {
      "email": "admin@admin.com",
      "password": "admin123"
    }
    ```

  - **Response (`200 OK`):**

    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlhd...NzE2MjM5Miwicm9sZXMiOlsiUk9MRV9BRE1JTiJdfQ.signature",
      "email": "admin@admin.com",
      "roles": ["ROLE_ADMIN"]
    }
    ```

-----

###  Address APIs ( Requires JWT token)

**Access Control:**

  * **Users (`ROLE_USER`)**: Can manage their own addresses.

#### 1\. Add a New Address

Adds a new shipping or billing address for the currently authenticated user.

  - **Method:** `POST`

  - **Endpoint:** `/api/addresses`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)

  - **Request Body:**

    ```json
    {
    "address": "123, Park Street, Pune, MH 411001"
     }
    ```

  - **Response (`200 OK`):**

    ```json
    {
    "addressId": 1,
    "address": "123, Park Street, Pune, MH 411001"
    }
    ```

#### 2\. Get All Addresses

Retrieves a list of all addresses associated with the authenticated user.

  - **Method:** `GET`

  - **Endpoint:** `/api/addresses`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)

  - **Response (`200 OK`):**

    ```json
    [
      {
    "addressId": 1,
    "address": "123, Park Street, Pune, MH 411001"
    },
      {
        "addressId": 2,
        "address": "456 Oak Ave,Someville ,NY,10001 USA"
      }
    ]
    ```

#### 3\. Update Address by ID

Updates an existing address by its ID for the current user.

  - **Method:** `PUT`

  - **Endpoint:** `/api/addresses/{id}` (e.g., `/api/addresses/1`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)

  - **Request Body:**

    ```json
    {
       "address": "456 Oak Ave,Someville ,NY,10001 USA"
    }
    ```

  - **Response (`200 OK`):**

    ```json
    {
      "addressId": 1,
       "address": "456 Oak Ave,Someville ,NY,10001 USA"
    }
    ```

#### 4\. Delete Address by ID

Deletes a specific address by its ID for the current user.

  - **Method:** `DELETE`
  - **Endpoint:** `/api/addresses/{id}` (e.g., `/api/addresses/1`)
  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)
  - **Response (`204 No Content`):** Successful deletion.

-----

### Cart APIs ( Requires JWT token)

**Access Control:**

  * **Users (`ROLE_USER`)**: Can manage their own shopping cart.

#### 1\. Get Current User's Cart Items

Retrieves all items currently in the authenticated user's shopping cart.

  - **Method:** `GET`

  - **Endpoint:** `/api/cart`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)

  - **Response (`200 OK`):**

    ```json
    [
      {
        "id": 101,
        "productId": 501,
        "productName": "Laptop Pro X",
        "quantity": 1,
        "price": 1200.00
      },
      {
        "id": 102,
        "productId": 502,
        "productName": "Wireless Mouse",
        "quantity": 2,
        "price": 25.00
      }
    ]
    ```

#### 2\. Add Product to Cart

Adds a specified product and quantity to the authenticated user's shopping cart. If the product is already in the cart, its quantity will be updated.

  - **Method:** `POST`

  - **Endpoint:** `/api/cart/add`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)

  - **Request Body:**

    ```json
    {
      "productId": 503,
      "quantity": 1
    }
    ```

  - **Response (`200 OK`):** Returns the updated cart.

    ```json
    {
      "id": 1,
      "userId": 456,
      "items": [
        {
          "id": 101,
          "productId": 501,
          "productName": "Laptop Pro X",
          "quantity": 1,
          "price": 1200.00
        },
        {
          "id": 103,
          "productId": 503,
          "productName": "Keyboard",
          "quantity": 1,
          "price": 75.00
        }
      ],
      "totalAmount": 1275.00
    }
    ```

#### 3\. Remove Product from Cart

Removes a specific product entirely from the authenticated user's cart.

  - **Method:** `DELETE`

  - **Endpoint:** `/api/cart/remove/{productId}` (e.g., `/api/cart/remove/502`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)

  - **Response (`200 OK`):** Returns the updated cart after removal.

    ```json
    {
      "id": 1,
      "userId": 456,
      "items": [
        {
          "id": 101,
          "productId": 501,
          "productName": "Laptop Pro X",
          "quantity": 1,
          "price": 1200.00
        }
      ],
      "totalAmount": 1200.00
    }
    ```

#### 4\. Update Quantity of Product in Cart

Updates the quantity of a specific product in the authenticated user's cart.

  - **Method:** `PUT`

  - **Endpoint:** `/api/cart/update`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)

  - **Request Body:**

    ```json
    {
      "productId": 501,
      "quantity": 2
    }
    ```

  - **Response (`200 OK`):** Returns the updated cart.

    ```json
    {
      "id": 1,
      "userId": 456,
      "items": [
        {
          "id": 101,
          "productId": 501,
          "productName": "Laptop Pro X",
          "quantity": 2,
          "price": 2400.00
        }
      ],
      "totalAmount": 2400.00
    }
    ```

#### 5\. Clear All Items in Cart

Removes all items from the authenticated user's shopping cart.

  - **Method:** `DELETE`
  - **Endpoint:** `/api/cart/clear`
  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)
  - **Response (`204 No Content`):** Cart cleared successfully.

-----

###  Category APIs ( Requires JWT token)

**Access Control:**

  * **Users (`ROLE_USER`)**: Can view categories.
  * **Admins (`ROLE_ADMIN`)**: Can create, update, and delete categories.

#### 1\. Add a New Category

Allows an administrator to add a new product category.

  - **Method:** `POST`

  - **Endpoint:** `/api/categories`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Request Body:**

    ```json
    {
      "name": "Electronics",
      "description": "Electronic gadgets and devices."
    }
    ```

  - **Response (`200 OK`):**

    ```json
    {
      "id": 1,
      "name": "Electronics",
      "description": "Electronic gadgets and devices."
    }
    ```

#### 2\. Get All Categories

Retrieves a list of all product categories.

  - **Method:** `GET`

  - **Endpoint:** `/api/categories`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User or Admin Token)

  - **Response (`200 OK`):**

    ```json
    [
      {
        "id": 1,
        "name": "Electronics",
        "description": "Electronic gadgets and devices."
      },
      {
        "id": 2,
        "name": "Books",
        "description": "Fiction and non-fiction books."
      }
    ]
    ```

#### 3\. Update Category by ID

Allows an administrator to update an existing product category.

  - **Method:** `PUT`

  - **Endpoint:** `/api/categories/{id}` (e.g., `/api/categories/1`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Request Body:**

    ```json
    {
      "name": "Updated Electronics",
      "description": "All updated electronic items."
    }
    ```

  - **Response (`200 OK`):**

    ```json
    {
      "id": 1,
      "name": "Updated Electronics",
      "description": "All updated electronic items."
    }
    ```

#### 4\. Delete Category by ID

Allows an administrator to delete a product category.

  - **Method:** `DELETE`
  - **Endpoint:** `/api/categories/{id}` (e.g., `/api/categories/1`)
  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)
  - **Response (`204 No Content`):** Category deleted successfully.

#### 5\. Get Category by ID

Retrieves a single product category by its ID.

  - **Method:** `GET`

  - **Endpoint:** `/api/categories/{id}` (e.g., `/api/categories/1`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User or Admin Token)

  - **Response (`200 OK`):**

    ```json
    {
      "id": 1,
      "name": "Electronics",
      "description": "Electronic gadgets and devices."
    }
    ```

-----

###  Discount APIs ( Requires JWT token)

**Access Control:**

  * **Users (`ROLE_USER`)**: Can view discounts and compute prices with discounts.
  * **Admins (`ROLE_ADMIN`)**: Can create, view, and delete discounts.

#### 1\. Create Discount for Product

Allows an administrator to create a new discount specifically for a product.

  - **Method:** `POST`

  - **Endpoint:** `/api/discounts/product/{productId}` (e.g., `/api/discounts/product/101`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Query Parameters:**

      - `code`: (string, required) The discount code (e.g., "SAVE10").
      - `value`: (number, required) The discount value.
      - `isPercentage`: (boolean, required) True if `value` is a percentage, false for fixed amount.
      - `startDate`: (string, required) Start date of the discount (YYYY-MM-DD).
      - `endDate`: (string, required) End date of the discount (YYYY-MM-DD).

  - **Example Request:**
    `POST /api/discounts/product/101?code=SUMMER20&value=20&isPercentage=true&startDate=2025-08-01&endDate=2025-08-31`

  - **Response (`200 OK`):**

    ```json
    {
      "id": 1,
      "code": "SUMMER20",
      "value": 20.0,
      "isPercentage": true,
      "startDate": "2025-08-01",
      "endDate": "2025-08-31"
    }
    ```

#### 2\. List Discounts by Product

Retrieves all active discounts applied to a specific product.

  - **Method:** `GET`

  - **Endpoint:** `/api/discounts/product/{productId}` (e.g., `/api/discounts/product/101`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User or Admin Token)

  - **Response (`200 OK`):**

    ```json
    [
      {
        "id": 1,
        "code": "SUMMER20",
        "value": 20.0,
        "isPercentage": true,
        "startDate": "2025-08-01",
        "endDate": "2025-08-31"
      }
    ]
    ```

#### 3\. Compute Effective Price with Discount

Calculates the final price of a product after applying any eligible discounts.

  - **Method:** `GET`

  - **Endpoint:** `/api/discounts/product/{productId}/price` (e.g., `/api/discounts/product/101/price`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User or Admin Token)

  - **Query Parameters:**

      - `basePrice`: (number, required) The original price of the product.

  - **Example Request:**
    `GET /api/discounts/product/101/price?basePrice=100.00`

  - **Response (`200 OK`):** Returns the calculated effective price.

    ```json
    80.00
    ```

#### 4\. Delete Discount

Allows an administrator to delete an existing discount.

  - **Method:** `DELETE`

  - **Endpoint:** `/api/discounts/{discountId}` (e.g., `/api/discounts/1`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Response (`200 OK`):**

    ```json
    "Discount deleted successfully"
    ```

-----

###  Order APIs ( Requires JWT token)

**Access Control:**

  * **Users (`ROLE_USER`)**: Can place orders, view their own orders, and cancel their own orders.
  * **Admins (`ROLE_ADMIN`)**: Can view all orders, get specific orders, and update order statuses.

#### 1\. Get All Orders (Admin Only)

Retrieves a list of all orders in the system.

  - **Method:** `GET`

  - **Endpoint:** `/api/orders`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Response (`200 OK`):**

    ```json
    [
      {
        "id": 1,
        "userId": 456,
        "orderDate": "2025-08-17T10:00:00Z",
        "totalAmount": 1250.00,
        "status": "DELIVERED"
      },
      {
        "id": 2,
        "userId": 789,
        "orderDate": "2025-08-18T14:30:00Z",
        "totalAmount": 500.00,
        "status": "PENDING"
      }
    ]
    ```

#### 2\. Place Order and Pay (User Only)

Allows an authenticated user to place a new order. This endpoint expects payment details within the request body, facilitating an integrated order and payment process.

  - **Method:** `POST`

  - **Endpoint:** `/api/orders`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)

  - **Request Body:**

    ```json
    {
      "cartId": 1,
      "paymentMethod": "CREDIT_CARD",
      "amount": 1250.00,
      "cardNumber": "4111111111111111",
      "expiry": "12/26",
      "cvv": "123"
    }
    ```

  - **Response (`200 OK`):**

    ```json
    {
      "order": {
        "id": 1,
        "userId": 456,
        "orderDate": "2025-08-17T10:00:00Z",
        "totalAmount": 1250.00,
        "status": "PROCESSING"
      },
      "payment": {
        "id": 100,
        "orderId": 1,
        "method": "CREDIT_CARD",
        "amount": 1250.00,
        "transactionId": "txn_abc123",
        "status": "COMPLETED",
        "paymentDate": "2025-08-17T10:00:00Z"
      }
    }
    ```

#### 3\. Get Order by ID (Admin Only)

Retrieves the details of a specific order by its ID.

  - **Method:** `GET`

  - **Endpoint:** `/api/orders/admin/{id}` (e.g., `/api/orders/admin/1`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Response (`200 OK`):**

    ```json
    {
      "id": 1,
      "userId": 456,
      "orderDate": "2025-08-17T10:00:00Z",
      "totalAmount": 1250.00,
      "status": "DELIVERED"
    }
    ```

#### 4\. Update Order Status (Admin Only)

Allows an administrator to update the status of an order.

  - **Method:** `PUT`

  - **Endpoint:** `/api/orders/{id}/status` (e.g., `/api/orders/1/status`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Query Parameters:**

      - `status`: (string, required) The new status for the order (e.g., `SHIPPED`, `DELIVERED`, `CANCELLED`).

  - **Example Request:**
    `PUT /api/orders/1/status?status=SHIPPED`

  - **Response (`200 OK`):**

    ```json
    {
      "id": 1,
      "userId": 456,
      "orderDate": "2025-08-17T10:00:00Z",
      "totalAmount": 1250.00,
      "status": "SHIPPED"
    }
    ```

#### 5\. Get Orders of Current User

Retrieves a list of all orders placed by the currently authenticated user.

  - **Method:** `GET`

  - **Endpoint:** `/api/orders/my`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)

  - **Response (`200 OK`):**

    ```json
    [
      {
        "id": 1,
        "userId": 456,
        "orderDate": "2025-08-17T10:00:00Z",
        "totalAmount": 1250.00,
        "status": "DELIVERED"
      }
    ]
    ```

#### 6\. Cancel Order (User Only)

Allows an authenticated user to cancel their own order, typically if it's still in a cancellable state (e.g., `PENDING`, `PROCESSING`).

  - **Method:** `DELETE`
  - **Endpoint:** `/api/orders/{id}` (e.g., `/api/orders/2`)
  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)
  - **Response (`204 No Content`):** Order canceled successfully.

-----

###  Payment APIs ( Requires JWT token)

**Access Control:**

  * **Users (`ROLE_USER`)**: Can view their own payment details.
  * **Admins (`ROLE_ADMIN`)**: Can view any payment details and update payment statuses.

#### 1\. Get Payment Details

Retrieves the details of a specific payment by its ID.

  - **Method:** `GET`

  - **Endpoint:** `/api/payments/{paymentId}` (e.g., `/api/payments/100`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User or Admin Token)

  - **Response (`200 OK`):**

    ```json
    {
      "id": 100,
      "orderId": 1,
      "method": "CREDIT_CARD",
      "amount": 1250.00,
      "transactionId": "txn_abc123",
      "status": "COMPLETED",
      "paymentDate": "2025-08-17T10:00:00Z"
    }
    ```

#### 2\. Create Payment for Order

Initiates a payment for a specific order. This is typically part of the order placement process.

  - **Method:** `POST`

  - **Endpoint:** `/api/payments/order/{orderId}` (e.g., `/api/payments/order/2`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User or Admin Token)

  - **Query Parameters:**

      - `method`: (string, required) The payment method (`CREDIT_CARD`, `PAYPAL`, etc.).
      - `amount`: (number, required) The amount to be paid.
      - `transactionId`: (string, required) The unique transaction ID from the payment gateway.

  - **Example Request:**
    `POST /api/payments/order/2?method=PAYPAL&amount=500.00&transactionId=paypal_xyz456`

  - **Response (`200 OK`):**

    ```json
    {
      "id": 101,
      "orderId": 2,
      "method": "PAYPAL",
      "amount": 500.00,
      "transactionId": "paypal_xyz456",
      "status": "COMPLETED",
      "paymentDate": "2025-08-18T14:35:00Z"
    }
    ```

#### 3\. Update Payment Status (Admin Only)

Allows an administrator to update the status of a payment.

  - **Method:** `PUT`

  - **Endpoint:** `/api/payments/{paymentId}/status` (e.g., `/api/payments/100/status`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Query Parameters:**

      - `status`: (string, required) The new status for the payment (`PENDING`, `COMPLETED`, `FAILED`, `CANCELED`).

  - **Example Request:**
    `PUT /api/payments/100/status?status=FAILED`

  - **Response (`200 OK`):**

    ```json
    {
      "id": 100,
      "orderId": 1,
      "method": "CREDIT_CARD",
      "amount": 1250.00,
      "transactionId": "txn_abc123",
      "status": "FAILED",
      "paymentDate": "2025-08-17T10:00:00Z"
    }
    ```

-----

###  Product APIs ( Requires JWT token)

**Access Control:**

  * **Users (`ROLE_USER`)**: Can view products.
  * **Admins (`ROLE_ADMIN`)**: Can create, update, delete, and view products.

#### 1\. Add New Product (Admin Only)

Allows an administrator to add a new product to the catalog.

  - **Method:** `POST`

  - **Endpoint:** `/api/products`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Request Body:**

    ```json
    {
      "name": "Bluetooth Headphones",
      "description": "High-quality wireless headphones with noise cancellation.",
      "price": 150.00,
      "stockQuantity": 200,
      "discountPercentage": 5.0,
      "categoryId": 1
    }
    ```

  - **Response (`200 OK`):**

    ```json
    {
      "id": 101,
      "name": "Bluetooth Headphones",
      "description": "High-quality wireless headphones with noise cancellation.",
      "price": 150.00,
      "stockQuantity": 200,
      "discountPercentage": 5.0,
      "category": {
        "id": 1,
        "name": "Electronics",
        "description": "Electronic gadgets and devices."
      },
      "createdAt": "2025-08-18T15:00:00Z",
      "updatedAt": "2025-08-18T15:00:00Z"
    }
    ```

#### 2\. Get All Products

Retrieves a list of all products available in the store.

  - **Method:** `GET`

  - **Endpoint:** `/api/products`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User or Admin Token)

  - **Response (`200 OK`):**

    ```json
    [
      {
        "id": 101,
        "name": "Bluetooth Headphones",
        "description": "High-quality wireless headphones with noise cancellation.",
        "price": 150.00,
        "stockQuantity": 200,
        "discountPercentage": 5.0,
        "category": {
          "id": 1,
          "name": "Electronics"
        },
        "createdAt": "2025-08-18T15:00:00Z",
        "updatedAt": "2025-08-18T15:00:00Z"
      },
      {
        "id": 102,
        "name": "E-Reader",
        "description": "Portable e-book reader.",
        "price": 80.00,
        "stockQuantity": 150,
        "discountPercentage": 0.0,
        "category": {
          "id": 2,
          "name": "Books"
        },
        "createdAt": "2025-08-18T15:10:00Z",
        "updatedAt": "2025-08-18T15:10:00Z"
      }
    ]
    ```

#### 3\. Update Product by ID (Admin Only)

Allows an administrator to update the details of an existing product.

  - **Method:** `PUT`

  - **Endpoint:** `/api/products/{id}` (e.g., `/api/products/101`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Request Body:**

    ```json
    {
      "name": "Bluetooth Headphones Pro",
      "description": "Updated high-quality wireless headphones with enhanced noise cancellation.",
      "price": 160.00,
      "stockQuantity": 180,
      "discountPercentage": 10.0,
      "categoryId": 1
    }
    ```

  - **Response (`200 OK`):**

    ```json
    {
      "id": 101,
      "name": "Bluetooth Headphones Pro",
      "description": "Updated high-quality wireless headphones with enhanced noise cancellation.",
      "price": 160.00,
      "stockQuantity": 180,
      "discountPercentage": 10.0,
      "category": {
        "id": 1,
        "name": "Electronics"
      },
      "createdAt": "2025-08-18T15:00:00Z",
      "updatedAt": "2025-08-18T15:30:00Z"
    }
    ```

#### 4\. Delete Product by ID (Admin Only)

Allows an administrator to remove a product from the catalog.

  - **Method:** `DELETE`
  - **Endpoint:** `/api/products/{id}` (e.g., `/api/products/101`)
  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)
  - **Response (`204 No Content`):** Product deleted successfully.

#### 5\. Get Product by ID

Retrieves the details of a single product by its ID.

  - **Method:** `GET`

  - **Endpoint:** `/api/products/{id}` (e.g., `/api/products/102`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User or Admin Token)

  - **Response (`200 OK`):**

    ```json
    {
      "id": 102,
      "name": "E-Reader",
      "description": "Portable e-book reader.",
      "price": 80.00,
      "stockQuantity": 150,
      "discountPercentage": 0.0,
      "category": {
        "id": 2,
        "name": "Books"
      },
      "createdAt": "2025-08-18T15:10:00Z",
      "updatedAt": "2025-08-18T15:10:00Z"
    }
    ```

-----

###  Review APIs ( Requires JWT token)

**Access Control:**

  * **Users (`ROLE_USER`)**: Can add reviews and delete their own reviews.
  * **Admins (`ROLE_ADMIN`)**: Can add, view, and delete any review.

#### 1\. Add a Review

Allows an authenticated user or admin to add a review for a specific product.

  - **Method:** `POST`

  - **Endpoint:** `/api/reviews`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User or Admin Token)

  - **Request Body:**

    ```json
    {
      "productId": 101,
      "rating": 5,
      "comment": "Amazing product! Totally worth it."
    }
    ```

  - **Response (`200 OK`):**

    ```json
    {
      "id": 1,
      "productId": 101,
      "userId": 456,
      "rating": 5,
      "comment": "Amazing product! Totally worth it.",
      "createdAt": "2025-08-18T16:00:00Z"
    }
    ```

#### 2\. Get Reviews Filtered by Product or User

Retrieves reviews, optionally filtered by `productId` or `userId`.

  - **Method:** `GET`

  - **Endpoint:** `/api/reviews`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User or Admin Token)

  - **Query Parameters:**

      - `productId`: (integer, optional) Filter reviews for a specific product.
      - `userId`: (integer, optional) Filter reviews by a specific user.

  - **Example Request (by product):**
    `GET /api/reviews?productId=101`

  - **Example Request (by user):**
    `GET /api/reviews?userId=456`

  - **Response (`200 OK`):**

    ```json
    [
      {
        "id": 1,
        "productId": 101,
        "userId": 456,
        "rating": 5,
        "comment": "Amazing product! Totally worth it.",
        "createdAt": "2025-08-18T16:00:00Z"
      }
    ]
    ```

#### 3\. Delete a Review by ID

Allows an authenticated user to delete their own review, or an administrator to delete any review.

  - **Method:** `DELETE`

  - **Endpoint:** `/api/reviews/{reviewId}` (e.g., `/api/reviews/1`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User or Admin Token)

  - **Response (`200 OK`):**

    ```json
    "Review deleted successfully"
    ```

-----

### User APIs ( Requires JWT token)

**Access Control:**

  * **Users (`ROLE_USER`)**: Can view and update their own profile.
  * **Admins (`ROLE_ADMIN`)**: Can view all users, get specific users by ID, and delete users.

#### 1\. Get All Users (Admin Only)

Retrieves a list of all registered users in the system.

  - **Method:** `GET`

  - **Endpoint:** `/api/users`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Response (`200 OK`):**

    ```json
    [
      {
        "id": 456,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com",
        "mobileNumber": "9876543210",
        "roles": ["ROLE_USER"]
      },
      {
        "id": 789,
        "firstName": "Admin",
        "lastName": "User",
        "email": "admin@admin.com",
        "mobileNumber": "1234567890",
        "roles": ["ROLE_ADMIN"]
      }
    ]
    ```

#### 2\. Get User by ID (Admin Only)

Retrieves the profile details of a specific user by their ID.

  - **Method:** `GET`

  - **Endpoint:** `/api/users/admin/{id}` (e.g., `/api/users/admin/456`)

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)

  - **Response (`200 OK`):**

    ```json
    {
      "id": 456,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "mobileNumber": "9876543210",
      "roles": ["ROLE_USER"]
    }
    ```

#### 3\. Delete User by ID (Admin Only)

Allows an administrator to delete a user account by its ID.

  - **Method:** `DELETE`
  - **Endpoint:** `/api/users/admin/{id}` (e.g., `/api/users/admin/456`)
  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (Admin Token)
  - **Response (`204 No Content`):** User deleted successfully.

#### 4\. Get Current User's Profile

Retrieves the profile details of the currently authenticated user.

  - **Method:** `GET`

  - **Endpoint:** `/api/users/me`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)

  - **Response (`200 OK`):**

    ```json
    {
      "id": 456,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "mobileNumber": "9876543210",
      "roles": ["ROLE_USER"]
    }
    ```

#### 5\. Update Current User's Profile

Allows the currently authenticated user to update their own profile information.

  - **Method:** `PUT`

  - **Endpoint:** `/api/users/me`

  - **Headers:** `Authorization: Bearer <JWT_TOKEN>` (User Token)

  - **Request Body:**

    ```json
    {
      "firstName": "Jonathan",
      "lastName": "Smith",
      "email": "jonathan.smith@example.com",
      "mobileNumber": "9988776655"
    }
    ```

  - **Response (`200 OK`):**

    ```json
    {
      "id": 456,
      "firstName": "Jonathan",
      "lastName": "Smith",
      "email": "jonathan.smith@example.com",
      "mobileNumber": "9988776655",
      "roles": ["ROLE_USER"]
    }
    ```
