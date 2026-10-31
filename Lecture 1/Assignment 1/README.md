
# Live Counter - Full Stack System Design Project

## 1. Project Overview

Live Counter is a full-stack web application that allows users to view and increment a shared counter.

The project demonstrates how a frontend, backend API, and relational database work together in a distributed application.

The counter value is stored in PostgreSQL, so the value is preserved even when the backend application is restarted.

This project is developed as part of my High-Level Design (HLD) learning journey.

## 2. Project Objectives

The main objectives are:

- Build a full-stack application using React and Spring Boot.
- Understand communication between frontend and backend.
- Design and expose REST APIs.
- Store application data in PostgreSQL.
- Understand database persistence.
- Learn about race conditions and concurrent requests.
- Use atomic database updates to handle concurrent increments.
- Containerize application components using Docker.
- Understand service-to-service communication using Docker Compose.

## 3. Technology Stack

| Component | Technology |
|---|---|
| Frontend | React |
| Frontend development server | Vite |
| Backend | Java Spring Boot |
| Backend build tool | Maven |
| API communication | REST / HTTP |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Containerization | Docker |
| Multi-container orchestration | Docker Compose |

## 4. System Architecture

The application consists of three main components:

### Frontend - React

The frontend provides a simple user interface containing:

- Counter value display.
- Increase button.
- Refresh button.

Responsibilities:

- Display the current counter value.
- Send HTTP requests to the backend.
- Update the displayed value using the backend response.
- Handle loading states and API errors.

### Backend - Spring Boot

The backend exposes REST API endpoints.

Responsibilities:

- Receive requests from the frontend.
- Retrieve the counter value from PostgreSQL.
- Increment the counter.
- Return the updated counter value.
- Handle database operations through Spring Data JPA.

### Database - PostgreSQL

PostgreSQL stores the counter value.

The counter is stored as a database record with an ID and value.

The application uses a persistent database so that the counter value survives backend restarts.

## 5. High-Level Architecture Diagram

```text
+---------------------------+
|       User / Browser      |
+---------------------------+
              |
              | HTTP
              v
+---------------------------+
|       React Frontend      |
|                           |
|  - Display Counter        |
|  - Increase Button        |
|  - Refresh Button         |
+---------------------------+
              |
              | REST API
              v
+---------------------------+
|    Spring Boot Backend    |
|                           |
|  - Counter Controller     |
|  - Counter Repository     |
|  - Business Logic         |
+---------------------------+
              |
              | JDBC
              v
+---------------------------+
|       PostgreSQL          |
|                           |
|  - Counter Table          |
|  - Persistent Value       |
+---------------------------+
```

## 6. Application Flow

### Fetch Counter

1. The user opens the application.
2. React sends a GET request to the backend.
3. Spring Boot retrieves the counter record from PostgreSQL.
4. If the record does not exist, the application initializes it with a value of 2.
5. The backend returns the counter value.
6. React displays the value.

### Increment Counter

1. The user clicks the Increase button.
2. React sends a POST request to the backend.
3. Spring Boot asks PostgreSQL to increment the counter.
4. PostgreSQL updates the stored value.
5. The backend retrieves the updated value.
6. The backend returns the updated value to React.
7. React updates the displayed counter.

### Refresh Counter

1. The user clicks Refresh.
2. React sends a GET request.
3. The backend retrieves the latest stored value.
4. React displays the returned value.

## 7. REST API Design

Base URL:

`http://localhost:8080/api/counter`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/counter` | Retrieve current counter value |
| POST | `/api/counter/increment` | Increment counter by 1 |

### GET /api/counter

Example response:

```json
{
  "value": 2
}
```

### POST /api/counter/increment

Example response:

```json
{
  "value": 3
}
```

## 8. Database Design

Database: `livecounter`

Table: `counter`

| Column | Type | Description |
|---|---|---|
| id | BIGINT | Primary key |
| value | INTEGER | Current counter value |

The application uses JPA entity mapping to map the Java Counter class to the PostgreSQL table.

## 9. Concurrency Handling

### Problem: Race Condition

When multiple users increment the counter simultaneously, a race condition can occur.

For example, suppose the current counter value is 10.

Two requests may read the value 10 at nearly the same time. Both may calculate 11 and save it.

The final value becomes 11 instead of 12.

This is known as a lost update problem.

### Solution: Atomic Database Update

The project uses a database-level increment operation:

```sql
UPDATE counter
SET value = value + 1
WHERE id = 1;
```

PostgreSQL performs the increment directly in the database.

This avoids the application-level read-modify-write pattern and makes the increment operation atomic.

The backend uses a transaction around the increment operation.

## 10. Docker and Docker Compose

The application uses Docker to run its components in containers.

Three services are defined:

| Service | Container | Port |
|---|---|---|
| Frontend | live-counter-frontend | 5173 |
| Backend | live-counter-backend | 8080 |
| Database | live-counter-db | 5432 |

Docker Compose manages the services together.

The backend connects to PostgreSQL using the Compose service name:

`database:5432`

The frontend communicates with the backend through:

`http://localhost:8080`

A named Docker volume is used to persist PostgreSQL data.

## 11. Running the Application

From the project root directory:

```bash
docker compose up --build
```

Access the frontend:

`http://localhost:5173`

Access the backend API:

`http://localhost:8080/api/counter`

Stop the application:

```bash
docker compose down
```

The PostgreSQL named volume is retained when using `docker compose down` without the `-v` option.

## 12. Key System Design Concepts Learned

Through this project, the following concepts are explored:

- Client-server architecture.
- Frontend and backend separation.
- REST API communication.
- Relational database design.
- Data persistence.
- Database transactions.
- Race conditions.
- Lost updates.
- Atomic database operations.
- Docker containerization.
- Docker Compose networking.
- Persistent Docker volumes.

## 13. Future Improvements

Potential improvements for future versions:

- Support multiple independent counters.
- Add user authentication.
- Add real-time counter updates using WebSockets.
- Add automated unit and integration tests.
- Add health checks for Docker Compose services.
- Add monitoring and application logging.
- Deploy the application to a cloud environment.

## 14. Project Structure

```text
live-counter/
├── frontend/
│   ├── src/
│   ├── Dockerfile
│   └── package.json
│
├── backend/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
└── docker-compose.yml
```

---

**Project:** Live Counter  
**Category:** Full Stack Application / System Design Learning  
**Status:** Learning project
