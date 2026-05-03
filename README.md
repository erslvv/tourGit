# SafeTrip

SafeTrip is a web application for tourists that visiting Almaty.  
It helps users explore tours, food places, entertainment spots, safety information and book tours in one platform.

## Problem Statement

Tourists, especially first-time visitors, often struggle to find trustworthy information about places in the city to visit, food spots, local safety rules and tour planning in one place.  
SafeTrip solves this problem by combining verified travel content, booking features and user-friendly navigation in a single system.

## Project Overview

The project consists of:

- a Spring Boot backend for APIs, authentication, booking logic and database operations
- a React frontend for user interaction and page navigation
- a PostgreSQL database for storing users, tours, places, favorites and bookings

## Features

- User registration and login with JWT authentication
- Browse tours, food places, and entertainment places
- View tour details and place details
- Save favorite places and tours to the user profile
- Book tours and receive a ticket code
- Cancel bookings from the user profile
- Admin panel for creating and managing tours and places
- Search by name in Tours, Food, and Entertainment
- Safety information page for tourists
- Responsive frontend for desktop and mobile devices

## Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- Flyway

### Frontend

- React
- Vite
- Axios
- React Router
- CSS

### Database and Tools

- PostgreSQL
- Docker
- Docker Compose
- Swagger / Springdoc
- pgAdmin

## Project Structure

```text
tourGit/
├── .mvn/
├── safetrip-front/
├── src/
│   └── main/
│       ├── java/
│       └── resources/
├── README.md
├── AUDIT.md
├── LICENSE
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## How to Run

### Run with Docker

From the project root folder, run:

```bash
docker compose up --build
```

After startup, open:

- Frontend: `http://localhost:3000`
- Backend: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- pgAdmin: `http://localhost:5050`

### Frontend for Development

```bash
cd safetrip-front
npm install
npm run dev
```

### Backend for Development

From the project root folder, run:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```bash
./mvnw.cmd spring-boot:run
```

## Usage

1. Register a new user account or log in.
2. Browse available tours, food places and entertainment spots.
3. Open a detail page to view more information.
4. Add interesting places or tours to favorites.
5. Book a tour and receive a ticket code.
6. Manage favorites and bookings from the profile page.
7. Admin users can create, update and manage tours and places.

## Team Members

- 230103174
- 230103215
- 230103128
- 230103289

## License

This project is created for educational purposes.
