# High-Concurrency Event Ticketing Engine

A backend system built with Spring Boot that simulates a real-world ticket booking platform.

The focus of this project is solving one of the most common concurrency problems in distributed systems: **preventing multiple users from booking the same seat at the same time**.

The application uses **JWT Authentication**, **PostgreSQL**, and **Redis Distributed Locking** to ensure that only one booking succeeds under concurrent access.

---

## Features

- User Registration & Login
- JWT-based Authentication
- Event Creation
- Automatic Seat Generation
- Seat Booking
- Redis Distributed Locking
- Bean Validation
- Global Exception Handling
- PostgreSQL Persistence
- Load Testing using k6

---

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Security
- JWT
- PostgreSQL
- Redis
- Redisson
- Spring Data JPA
- Maven
- k6

---

## Architecture

```
                Client
                   │
                   ▼
        JWT Authentication Filter
                   │
                   ▼
           Booking Controller
                   │
                   ▼
            Booking Service
                   │
                   ▼
        Redis Distributed Lock
            (seat:<seatId>)
                   │
                   ▼
             PostgreSQL Database
```

---

## Authentication Flow

```
Register
    │
    ▼
Login
    │
    ▼
JWT Token
    │
    ▼
Protected APIs
```

---

## Booking Flow

```
Booking Request
        │
        ▼
Acquire Redis Lock
        │
        ▼
Fetch Seat
        │
        ▼
Seat Available ?
      │      │
     Yes     No
      │       │
      ▼       ▼
 Book Seat   409 Conflict
      │
      ▼
 Commit Transaction
      │
      ▼
Release Redis Lock
```

---

## Concurrency Journey

The project was developed by exploring different concurrency control strategies before settling on Redis Distributed Locking.

| Strategy | Result |
|----------|--------|
| Normal JPA | Race condition observed |
| Optimistic Locking | Prevented duplicate updates but required retries under contention |
| Pessimistic Locking | Guaranteed consistency but reduced concurrency due to database row locks |
| Redis Distributed Locking | Final implementation used for scalable and reliable seat booking |

---

## API Endpoints

### Authentication

```
POST /auth/register
POST /auth/login
```

### Events

```
POST /api/events
GET  /api/events/{eventId}/seats
```

### Booking

```
POST /api/bookings
```

---

## Example Booking Request

```http
POST /api/bookings
Authorization: Bearer <JWT>
Content-Type: application/json
```

```json
{
    "seatId": 1
}
```

---

## Sample Response

```json
{
    "bookingId": 59,
    "bookingTime": "2026-07-17T00:54:39",
    "eventTitle": "Coldplay Live",
    "seatNumber": "A1"
}
```

---

## Load Testing

The booking endpoint was tested using **k6** with concurrent authenticated requests targeting the same seat.

Result:

- Only one booking succeeds.
- Remaining requests receive **HTTP 409 Conflict**.
- No duplicate bookings are created.

This demonstrates that the Redis distributed lock successfully prevents double booking under concurrent access.

---

## Running Locally

```bash
git clone <repository-url>

cd Ticket-Allocation
```

Configure PostgreSQL and Redis in `application.properties`.

Run Redis:

```bash
redis-server
```

Start the application:

```bash
./mvnw spring-boot:run
```

Run the load test:

```bash
k6 run booking-test.js
```
