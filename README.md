# Booking System API

A RESTful Booking System built with **Spring Boot 3.x**, **Java 21**, and **MySQL**. Supports role-based access control (RBAC) and JWT authentication for secure access to resources and reservations.

---

## Tech Stack

- **Java 21** (language level 21)  
- **Spring Boot 3.x**  
- **Spring Security (JWT)**  
- **Spring Data JPA**  
- **MySQL**  
- **Lombok**  
- **ModelMapper** (for DTO mapping)  

---

## Setup & Installation

### 1. Create the database

```sql
CREATE DATABASE booking;
```
# MySQL Database

spring.datasource.url=jdbc:mysql://localhost:3306/booking?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secretKey=u0IYj9kHRy6XrHqLZJhF3QhwD2v3JgVbqBObX99eEjQ=

# Database Seeding

Upon first startup, the system automatically seeds two users:

| Username | Password | Role  |
| -------- | -------- | ----- |
| admin    | admin123 | ADMIN |
| user     | user123  | USER  |
ADMIN has full access.

USER can view resources and manage own reservations only

Authentication
Login to get JWT token

```json
{
  "username": "user",
  "password": "user123"
}
```


# API Endpoints
Resources (Vehicles)

| Method | Endpoint        | Role       | Description                    |
| ------ | --------------- | ---------- | ------------------------------ |
| GET    | /resources      | ADMIN/USER | List all resources (paginated) |
| GET    | /resources/{id} | ADMIN/USER | Get resource by ID             |
| POST   | /resources      | ADMIN      | Create resource                |
| PUT    | /resources/{id} | ADMIN      | Update resource                |
| DELETE | /resources/{id} | ADMIN      | Delete resource                |


# Reservations

| Method | Endpoint           | Role       | Description                      |
| ------ | ------------------ | ---------- | -------------------------------- |
| GET    | /reservations      | ADMIN/USER | List reservations (with filters) |
| GET    | /reservations/{id} | ADMIN/USER | Get reservation by ID            |
| POST   | /reservations      | ADMIN/USER | Create a reservation             |
| PUT    | /reservations/{id} | ADMIN/USER | Update a reservation             |
| DELETE | /reservations/{id} | ADMIN/USER | Delete a reservation             |


## Postman Collection

You can explore and test the API using the Postman collection:

**[Open Postman Collection](https://navify-developers.postman.co/workspace/Navify-Developers-Workspace~d69eb1ab-ac7d-4333-8930-ff411de2b241/request/37010247-664829ee-c14c-4bba-85e6-881cb01a82f1?action=share&creator=37010247&ctx=documentation&active-environment=37010247-0df01bb3-104d-48b0-bb7e-9b541ecc1660)**

