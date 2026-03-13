# Ticket Management System

A production-style **Spring Boot REST API** for managing application support tickets. This project was built to mirror common **application support and middleware support workflows**: tracking incidents, updating priorities, managing lifecycle states, validating user input, and logging backend operations for troubleshooting.

## Why this project
This project was designed to understand back application development:
- diagnosing and resolving web application issues
- working with Java-based backend services
- using relational databases to store and retrieve operational data
- testing APIs and validating business logic
- documenting maintainable service behavior

## Tech Stack
- **Java 17**
- **Spring Boot 3**
- **Spring Web**
- **Spring Data JPA / Hibernate**
- **MySQL**
- **JUnit 5 + Mockito**
- **Postman**
- **GitHub Actions**

## Features
- Create support tickets
- Retrieve all tickets or get a ticket by ID
- Update ticket **status** and **priority** independently
- Filter tickets by `status` and/or `priority`
- Validate request payloads using Spring validation
- Return consistent API errors using centralized exception handling
- Log create, read, update, and delete operations for traceability
- Seed sample ticket data for quick testing
- Include unit tests and a Postman collection

## Ticket Lifecycle
The application models a 3-stage support lifecycle:
- `OPEN`
- `IN_PROGRESS`
- `RESOLVED`

Priority levels:
- `LOW`
- `MEDIUM`
- `HIGH`

## API Endpoints
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/tickets` | Create a ticket |
| GET | `/api/tickets` | Retrieve all tickets |
| GET | `/api/tickets/{id}` | Retrieve a ticket by ID |
| PUT | `/api/tickets/{id}/status` | Update ticket status |
| PUT | `/api/tickets/{id}/priority` | Update ticket priority |
| DELETE | `/api/tickets/{id}` | Delete a ticket |

### Optional Filters
You can filter tickets using query parameters:
- `GET /api/tickets?status=OPEN`
- `GET /api/tickets?priority=HIGH`
- `GET /api/tickets?status=IN_PROGRESS&priority=MEDIUM`

## Example Requests
### Create Ticket
```json
{
  "title": "Integration service timeout",
  "description": "Middleware service times out during downstream API call.",
  "priority": "HIGH"
}
```

### Update Status
```json
{
  "status": "RESOLVED"
}
```

### Update Priority
```json
{
  "priority": "LOW"
}
```

## Example Response
```json
{
  "id": 1,
  "title": "Integration service timeout",
  "description": "Middleware service times out during downstream API call.",
  "status": "OPEN",
  "priority": "HIGH",
  "createdAt": "2026-03-13T10:15:23.432",
  "updatedAt": "2026-03-13T10:15:23.432"
}
```

## Validation and Error Handling
This project includes:
- field-level validation for required inputs
- centralized error handling via `@RestControllerAdvice`
- a consistent JSON error response structure for invalid requests and missing resources

Example validation error shape:
```json
{
  "timestamp": "2026-03-13T10:20:11.101",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "validationErrors": {
    "title": "Title is required"
  }
}
```

## Logging
Service operations log key application events such as:
- ticket creation
- ticket retrieval
- status changes
- priority changes
- deletion

This supports troubleshooting and mirrors the type of traceability expected in application support environments.

## Testing
The project includes **11 unit tests** covering:
- ticket creation
- retrieval by ID
- not-found behavior
- filter logic
- status update logic
- priority update logic
- delete behavior

Tests are implemented using **JUnit 5** and **Mockito**.

## How to Run Locally
### 1. Clone the repository
```bash
git clone https://github.com/your-username/ticket-management-system.git
cd ticket-management-system
```

### 2. Configure MySQL
Update `src/main/resources/application.properties` with your local MySQL credentials if needed:
```properties
spring.datasource.username=root
spring.datasource.password=password
```

Make sure MySQL is running locally.

### 3. Run the application
```bash
mvn spring-boot:run
```

The API will start on:
```text
http://localhost:8080
```

### 4. Test with Postman
Import the collection from:
```text
postman/Ticket-Management-System.postman_collection.json
```

## Project Structure
```text
src/main/java/com/example/ticketsystem
├── controller
├── dto
├── exception
├── model
├── repository
├── service
│   └── impl
└── TicketManagementSystemApplication.java
```

## CI Pipeline
A basic **GitHub Actions** workflow is included to run Maven tests on pushes and pull requests.

## Resume-Relevant Highlights
This repository supports claims such as:
- built **6 RESTful API endpoints** for ticket workflows
- implemented a **3-stage incident lifecycle**
- added validation and centralized exception handling across endpoints
- integrated structured logging for backend operations
- wrote **10+ unit tests** for service-layer behavior

## Future Improvements
- role-based authentication with Spring Security
- ticket assignment and comments
- Swagger / OpenAPI documentation
- pagination and sorting
- Dockerized local setup

## License
This project is provided for educational and portfolio use.
